package com.konovalov.edu.rest;

import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.entity.Employee;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.konovalov.edu.processes.ProcessDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/processes")
public class ProcessController {

    private EmployeeDao employeeDao;
    private final static String ID = "employeeId";
    private final static String REQ_DAYS = "requestedDays";

    @Autowired
    public ProcessController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping(value = "/getTasks")
    @ResponseBody
    public Object getAvailableTasks() {
        String json_response = ProcessDemo.getInstance().buildTasksJson();
        return new ResponseEntity<String>(json_response, HttpStatus.OK);
    }

    @PostMapping(value = "/approveUserVacation/{taskId}")
    @ResponseBody
    public Object initiateUserVacation(@PathVariable("taskId") Integer taskId) {

        String string;

        List<Task> tasks = ProcessDemo.getInstance().
                getTaskService().
                createTaskQuery().
                list();

        if (taskId >= tasks.size()) {
            HttpStatus stat = HttpStatus.BAD_REQUEST;
            string = stat + ": unknown task id.";
            return new ResponseEntity<String>(string, stat);
        }

        Task task = tasks.get(taskId);
        ProcessDemo.getInstance().
                getTaskService()
                .complete(task.getId());

        string = "Task " + taskId + " completed";

        return new ResponseEntity<String>(string, HttpStatus.OK);

    }

    @PostMapping(value = "/requestVacation")
    @ResponseBody
    public ResponseEntity<String> getAvailableProcesses(@RequestBody String employee_requested_data) {
        JSONObject jsonObject = new JSONObject(employee_requested_data);
        Integer employee_id = (Integer) jsonObject.get(ID);
        Integer requested_days = (Integer) jsonObject.get(REQ_DAYS);
        Employee employee = employeeDao.getEmployeeById(employee_id);
        if (employee != null) {

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("employee", employee_id);
            variables.put("nrOfHolidays", requested_days);

            ProcessInstance processInstance = ProcessDemo.getInstance().
                    getRuntimeService().
                    startProcessInstanceByKey("vac_req_shrink", variables);

            String process_id = processInstance.getId();

            return new ResponseEntity<>("Process id " +
                    process_id + " with name " +
                    processInstance.getName() +
                    " created", HttpStatus.OK);
        }
        ;
        String str = "Employee does not exist";
        return new ResponseEntity<>(str, HttpStatus.OK);
    }


}
