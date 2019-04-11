package com.konovalov.edu.rest;

import com.konovalov.edu.commonTypes.CommonTypes;
import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.dao.VacationDao;
import com.konovalov.edu.entity.Employee;
import com.konovalov.edu.entity.Vacation;
import com.konovalov.edu.entity.combinedentity.UserEmployee;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.konovalov.edu.processes.ProcessEngineImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO(ipolyakov): may be assign to group (process engine reference)

@CrossOrigin
@RestController
@RequestMapping("/processes")
public class ProcessController {

    private EmployeeDao employeeDao;
    private VacationDao vacationDao;
    private UserDao userDao;

    private HttpHeaders responseHeaders;

    @Autowired
    public ProcessController(EmployeeDao employeeDao,
                             VacationDao vacationDao,
                             UserDao userDao) {

        this.employeeDao = employeeDao;
        this.vacationDao = vacationDao;
        this.userDao = userDao;

        this.responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
    }

    // TODO(ipolyakov): add role/user id in request?
    @GetMapping(value = "/allTasks")
    @ResponseBody
    public Object getAllTasks() {
        HttpStatus responseCode = HttpStatus.OK;
        List<Task> tasks =
                ProcessEngineImpl.
                        getInstance().getOrderedTaskList();
        String json_response = ProcessEngineImpl.getInstance().buildTasksReport(tasks);
        if (tasks.isEmpty()) {
            return new ResponseEntity<String>(json_response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(json_response, responseHeaders, responseCode);
    }

    @PostMapping(value = "/approveUserVacation/{taskId}")
    @ResponseBody
    public Object approveUserVacation(@PathVariable("taskId") Integer indexTaskId,
                                      @RequestBody CommonTypes.approveStruct approveStruct) {
        String string;
        List<Task> tasks = ProcessEngineImpl.getInstance().
                getOrderedTaskList();
        HttpStatus stat;

        if (indexTaskId >= tasks.size()) {
            stat = HttpStatus.NOT_FOUND;
            return new ResponseEntity<String>("Task not found", stat);
        }
        if (indexTaskId < 0) {
            stat = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>("Task id MUST BE positive", stat);
        }
        Task task = tasks.get(indexTaskId);
        String taskId = task.getId();
        Map<String, Object> processVariables = ProcessEngineImpl.getInstance().
                getTaskService().
                getVariables(taskId);
        boolean waitState =
                processVariables.get("vacationStatus") ==
                        CommonTypes.requestStatus.WAIT;
        if (!waitState) {
            stat = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>("", stat);
        }

        CommonTypes.requestStatus completeStatus;
        Vacation vacation = vacationDao.getVacationById((int) processVariables.get("vacationId"));
        completeStatus = (approveStruct.approveStatus) ?
                CommonTypes.requestStatus.APPROVED :
                CommonTypes.requestStatus.DISAPPROVED;
        vacation.setVacationStatus(completeStatus);
        vacationDao.updateVacation(vacation);

        if (completeStatus == CommonTypes.requestStatus.APPROVED) {
            // фантастическая хуета
            Employee currentEmployee = employeeDao.getEmployeeById(vacation.getEmployeeId());
            int oldCharge = currentEmployee.getVacationDays();
            int requestedDays = vacation.getVacationDays();
            currentEmployee.setVacationDays(oldCharge - requestedDays);
            employeeDao.updateEmployee(currentEmployee);
        }

        processVariables.replace("vacationStatus", completeStatus);
        processVariables.replace("prevStateAssignedManagerId", task.getAssignee());
        processVariables.replace("prevStateAssignedManagerName", processVariables.get("managerName"));
        processVariables.replace("managerName", "");

        ProcessEngineImpl.getInstance()
                .getTaskService()
                .complete(taskId, processVariables);

        return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/vacationProcess")
    @ResponseBody
    public ResponseEntity<String> requestVacation(@RequestBody Vacation vacation) {
        if (vacation.getEmployeeId() == null ||
                vacation.getTypeId() == null ||
                vacation.getVacationStart() == null ||
                vacation.getVacationDays() == null)
            return new ResponseEntity<>(
                    "One or more required fields missing",
                    HttpStatus.BAD_REQUEST);

        Employee fake_cache_employee =
                employeeDao.
                        getEmployeeById(vacation.getEmployeeId());
        int requestedDays = vacation.getVacationDays();
        Date startDate = vacation.getVacationStart();
        String errorMessage = "";
        Date currentDate = new Date();
        if (fake_cache_employee != null) {
            // check if vacation start date >= current time
            // may be cast to UTC time
            if (startDate.compareTo(currentDate) >= 0)
                if (requestedDays <= fake_cache_employee.getVacationDays() && requestedDays > 0) {

                    // current vacation status
                    CommonTypes.requestStatus currentStatus = CommonTypes.requestStatus.WAIT;
                    vacation.setVacationStatus(currentStatus);

                    // if dangerous id received
                    if (vacation.getVacationId() != null)
                        return new ResponseEntity<>(
                                "Unexpected id received",
                                HttpStatus.BAD_REQUEST);

                    vacationDao.addVacation(vacation);

                    // fancy and huge manager assignment election
                    // obviously there are implementation in flowabale API
                    // but who cares?
                    List<UserEmployee> availableUsers = userDao.getAllUsersWithEmpById();
                    Integer managerId = null;
                    String managerName = null;
                    String currentManagerName = null;
                    TaskQuery query = ProcessEngineImpl.getInstance()
                            .getTaskService()
                            .createTaskQuery();
                    int minQueueSize = Integer.MAX_VALUE;
                    for (UserEmployee user : availableUsers) {
                        if (user.getRoleName().equals(CommonTypes.userRole.MANAGER.getName())) {
                            if (query.taskAssignee(user.getUserId().toString()) != null) {
                                int currenstSize = query.taskAssignee(user.getUserId().toString()).list().size();
                                if (currenstSize < minQueueSize) {
                                    minQueueSize = currenstSize;
                                    managerId = user.getUserId();
                                    managerName = user.getFirstName() + " " + user.getSecondName();
                                    continue;
                                }
                            } else {
                                managerId = user.getUserId();
                                managerName = user.getFirstName() + " " + user.getSecondName();
                            }
                        }
                    }

                    // map data to process engine reference information
                    long millisecondsSinceEpoch = vacation.getVacationStart().getTime();
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("employee", vacation.getEmployeeId());
                    variables.put("nrOfHolidays", vacation.getVacationDays());
                    variables.put("startDate", millisecondsSinceEpoch);
                    variables.put("vacationId", vacation.getVacationId());
                    variables.put("vacationStatus", currentStatus);
                    variables.put("vacationTypeId", vacation.getTypeId());
                    variables.put("managerName", managerName);
                    variables.put("prevStateAssignedManagerName", "");
                    variables.put("prevStateAssignedManagerId", null);

                    ProcessInstance processInstance = ProcessEngineImpl.getInstance().
                            getRuntimeService().
                            startProcessInstanceByKey("vac_req_shrink", variables);

                    Task task = ProcessEngineImpl.getInstance()
                            .getTaskService()
                            .createTaskQuery()
                            .processInstanceId(processInstance.getId()).singleResult();

                    // fancy owner setter
                    if (managerId != null)
                        ProcessEngineImpl.getInstance().getTaskService().setAssignee(task.getId(), managerId.toString());
                    ProcessEngineImpl.getInstance().getTaskService().setOwner(task.getId(), vacation.getEmployeeId().toString());

                    return new ResponseEntity<>("", responseHeaders, HttpStatus.CREATED);
                } else {
                    errorMessage = "Incorrect vacation days value";
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }
            else {
                errorMessage = "Vacation date MUST BE in future";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }
        errorMessage = "Employee not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    // USE CAREFULLY!
    // TODO(ipolyakov): remove after debug
    @PostMapping(value = "/deleteAllTasks")
    @ResponseBody
    public ResponseEntity<String> deleteAllTasks() {
        String str;
        List<Task> tasks = ProcessEngineImpl.getInstance().getTaskService().createTaskQuery().list();
        if (tasks.size() > 0) {
            str = "{ \"message\":\"" + tasks.size() + " tasks deleted\"}";
            RuntimeService runtimeService = ProcessEngineImpl.getInstance().getRuntimeService();
            TaskService taskService = ProcessEngineImpl.getInstance().getTaskService();

            for (Task task : tasks) {
                runtimeService.deleteProcessInstance(task.getProcessInstanceId(), " ");
                taskService.deleteTask(task.getId(), true);
            }
        } else {
            str = "{ \"message\" : \"tasks queue is empty\"}";
        }

        return new ResponseEntity<>(str, HttpStatus.OK);
    }


}
