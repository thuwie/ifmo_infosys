package com.konovalov.edu.rest;

import com.konovalov.edu.commonTypes.CommonTypes;
import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.dao.VacationDao;
import com.konovalov.edu.entity.Employee;
import com.konovalov.edu.entity.Vacation;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.konovalov.edu.processes.ProcessDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO(ipolyakov): assign owner
//TODO(ipolyakov): assing to manager
//TODO(ipolyakov): may be assign to group (process engine reference)

@CrossOrigin
@RestController
@RequestMapping("/processes")
public class ProcessController {

    public static class approveStruct {
        private boolean approveStatus;

        approveStruct() {
        }
    }

    private EmployeeDao employeeDao;
    private VacationDao vacationDao;

    @Autowired
    public ProcessController(EmployeeDao employeeDao, VacationDao vacationDao) {
        this.employeeDao = employeeDao;
        this.vacationDao = vacationDao;
    }

    @GetMapping(value = "/getTasks")
    @ResponseBody
    public Object getAvailableTasks() {
        // TODO(ipolyiakov): determinate task id (index) order assignment
        String json_response = ProcessDemo.getInstance().buildTasksReport();
        return new ResponseEntity<String>(json_response, HttpStatus.OK);
    }

    //TODO(ipolyakov): ASAP fix only disapprove (09.04.19)
    @PostMapping(value = "/approveUserVacation/{taskId}")
    @ResponseBody
    public Object approveUserVacation(@PathVariable("taskId") Integer taskId,
                                      @RequestBody approveStruct approveStruct) {

        String string;

        List<Task> tasks = ProcessDemo.getInstance().
                getTaskService().
                createTaskQuery().
                list();

        if (taskId >= tasks.size()) {
            HttpStatus stat = HttpStatus.BAD_REQUEST;
            string = "{ \"message\": \"unknown task id\"}";
            return new ResponseEntity<String>(string, stat);
        }

        Task task = tasks.get(taskId);
        String strTaskId = task.getId();

        Map<String, Object> processVariables = ProcessDemo.getInstance().
                getTaskService().
                getVariables(strTaskId);

        CommonTypes.requestStatus completeStatus;
        Vacation vacation = vacationDao.getVacationById((int)processVariables.get("vacationId"));
        completeStatus = (approveStruct.approveStatus) ?
                CommonTypes.requestStatus.APPROVED :
                CommonTypes.requestStatus.DISAPPROVED;
        vacation.setVacationStatus(completeStatus);
        vacationDao.updateVacation(vacation);

        if(completeStatus == CommonTypes.requestStatus.APPROVED) {
            // фантастическая хуета
            Employee currentEmployee = employeeDao.getEmployeeById(vacation.getEmployeeId());
            int oldCharge = currentEmployee.getVacationDays();
            int requestedDays = vacation.getVacationDays();
            currentEmployee.setVacationDays(oldCharge - requestedDays);
            employeeDao.updateEmployee(currentEmployee);
        }

        processVariables.replace("vacationStatus", completeStatus.name());

        ProcessDemo.getInstance()
                .getTaskService()
                .complete(strTaskId, processVariables);

        string = "{ \"message\": \"Task " + taskId + " completed with status " + completeStatus.name() + "\"}";

        return new ResponseEntity<String>(string, HttpStatus.OK);

    }

    @PostMapping(value = "/requestVacation")
    @ResponseBody
    public ResponseEntity<String> requestVacation(@RequestBody Vacation vacation) {
        Employee fake_cache_employee =
                employeeDao.
                        getEmployeeById(vacation.getEmployeeId());

        int requestedDays = vacation.getVacationDays();
        if (fake_cache_employee != null)
            // check if vacation start date >= current time
            // may be cast to UTC time
            if (requestedDays <= fake_cache_employee.getVacationDays() && requestedDays > 0) {

                // current vacation status
                CommonTypes.requestStatus currentStatus = CommonTypes.requestStatus.WAIT;
                vacation.setVacationStatus(currentStatus);

                // if dangerous id received
                if(vacation.getVacationId() != null)
                    vacation.setVacationId(null);

                vacationDao.addVacation(vacation);

                // map data to process engine reference information
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("employee", vacation.getEmployeeId());
                variables.put("nrOfHolidays", vacation.getVacationDays());
                variables.put("vacationId", vacation.getVacationId());
                variables.put("vacationStatus", currentStatus.name());
                variables.put("vacationStartDate", vacation.getVacationStart());
                variables.put("vacationTypeId", vacation.getTypeId());

                ProcessInstance processInstance = ProcessDemo.getInstance().
                        getRuntimeService().
                        startProcessInstanceByKey("vac_req_shrink", variables);

                String process_id = processInstance.getId();
                return new ResponseEntity<>("{ \"message\": \"vacation initiated: process id " +
                        process_id +
                        " created \"}", HttpStatus.OK);
            }

        String str = "{\"message\": \"employee" + vacation.getEmployeeId() + " does not exist\"}";
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    // USE CAREFULLY!
    // TODO(ipolyakov): remove after debug
    @PostMapping(value = "/deleteAllTasks")
    @ResponseBody
    public ResponseEntity<String> deleteAllTasks() {
        String str;
        List<Task> tasks = ProcessDemo.getInstance().getTaskService().createTaskQuery().list();
        if(tasks.size() > 0) {
            str = "{ \"message\" :" + tasks.size() + "\" tasks deleted\"}";
            RuntimeService runtimeService = ProcessDemo.getInstance().getRuntimeService();
            TaskService taskService = ProcessDemo.getInstance().getTaskService();

            for (Task task : tasks) {
                runtimeService.deleteProcessInstance(task.getProcessInstanceId(), " ");
                taskService.deleteTask(task.getId(), true);
            }
        }
        else {
            str = "{ \"message\" : \"tasks queue is empty\"}";
        }

        return new ResponseEntity<>(str, HttpStatus.OK);
    }


}
