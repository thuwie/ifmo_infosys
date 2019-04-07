package com.konovalov.edu.processes;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.task.Event;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO(ipolyakov): Rename
// TODO(ipolyakov): Make it Statefull (processes/tasks states and events)

public class ProcessDemo {
    private static volatile ProcessDemo instance;

    private final ProcessEngine processEngine;

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public static ProcessDemo getInstance() {
        ProcessDemo local = instance;
        if (local == null) {
            synchronized (ProcessDemo.class) {
                local = instance;
                if (local == null) {
                    instance = local = new ProcessDemo();
                }
            }
        }
        return local;
    }

    public String buildTasksJson() {

        String string = "{ \"tasksCount\": ";
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();
        string += tasks.size();
        string += ", \"status\" :" + HttpStatus.OK;
        string += ", \"tasks\": [";
        if(tasks.size() > 0) {
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
        }
        string += "]}";
        return string;
    }

    private ProcessDemo() {
        // just creates resource table for flowable
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:postgresql://rc1a-vjev09iwfvbz9znn.mdb.yandexcloud.net:6432/db1")
                .setJdbcUsername("sysuser")
                .setJdbcPassword("myverypassword")
                .setJdbcDriver("org.postgresql.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("vacation_request_shrink.bpmn20.xml")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        taskService = processEngine.getTaskService();
        runtimeService = processEngine.getRuntimeService();
    }

}
