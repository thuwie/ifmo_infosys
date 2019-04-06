package com.konovalov.edu.rest;

import com.konovalov.edu.entity.User;
import lombok.extern.log4j.Log4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.konovalov.edu.processes.ProcessDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/processes")
public class ProcessController {

    @CrossOrigin
    @GetMapping(value = "/getTasks")
    @ResponseBody
    public Object getAvailableTasks() {

        String string = "{ \"tasksCount\": ";
        RuntimeService runtimeService =
                ProcessDemo.getInstance().processEngine.getRuntimeService();

        TaskService taskService = ProcessDemo.getInstance().processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();
        if(tasks.size() > 0) {
            string += tasks.size() + ", \"tasks\": [";
            boolean first = true;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                Map<String, Object> processVariables = taskService.getVariables(task.getId());
                if (!first)
                    string += ", ";
                first = false;
                string += "{ \"taskId\": " + i + ", \"processName\": \"" + task.getName() + "\", \"ownerName\" :";
                if(task.getOwner() == null || task.getOwner().length() == 0)
                    string+= " " + null + " ";
                else
                    string += " \"" + task.getOwner() + "\" ";
                string += ", \"requestedDays\": " + processVariables.get("nrOfHolidays") + ", "
                        + " \"employeeId\": " + processVariables.get("employee") + "}";
            }
            string += "]}";

            return new ResponseEntity<String>(string, HttpStatus.OK);
        }
        else{
            string = HttpStatus.NO_CONTENT + ": No tasks available.";
            return new ResponseEntity<String>(string, HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/approveUserVacation/{taskId}")
    @ResponseBody
    public Object initiateUserVacation(@PathVariable("taskId") Integer taskId) {

        String string;

        TaskService taskService = ProcessDemo.getInstance().processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();

        if(taskId > tasks.size()) {
            HttpStatus stat = HttpStatus.BAD_REQUEST;
            string = stat + ": unknown task id.";
            return new ResponseEntity<String>(string, stat);
        }

        Task task = tasks.get(taskId);
        taskService.complete(task.getId());

        string = "Task " + taskId + " completed";

        return new ResponseEntity<String>(string, HttpStatus.OK);

    }

}
