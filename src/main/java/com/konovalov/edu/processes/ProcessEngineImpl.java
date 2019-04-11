package com.konovalov.edu.processes;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

// TODO(ipolyakov): Make it Statefull (processes/tasks states and events)

public class ProcessEngineImpl {
    private static volatile ProcessEngineImpl instance;

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

    public static ProcessEngineImpl getInstance() {
        ProcessEngineImpl local = instance;
        if (local == null) {
            synchronized (ProcessEngineImpl.class) {
                local = instance;
                if (local == null) {
                    instance = local = new ProcessEngineImpl();
                }
            }
        }
        return local;
    }

    public List<Task> getOrderedTaskList() {
        return taskService.
                createTaskQuery().
                orderByProcessInstanceId().asc().list();
    }

    public String buildTasksReport(List<Task> tasks /*int authEmployeeId*/) {

        String string = "[";
        TaskService taskService = processEngine.getTaskService();

        if (tasks.size() > 0) {
            boolean first = true;
            for (int k = 0; k < tasks.size(); ++k) {
                Task task = tasks.get(k);
                Map<String, Object> processVariables = taskService.getVariables(task.getId());
                Integer employeeId = Integer.valueOf(processVariables.get("employee").toString());

                if (!first)
                    string += ", ";
                first = false;
                string += "{\"taskId\": " + k + ", \"processStateDefinition\":\"" + task.getName()
                        + "\", \"ownerId\":" + task.getOwner();
                string += ", \"requestedDays\":" + processVariables.get("nrOfHolidays")
                        + ", \"vacationStartDate\":" + processVariables.get("startDate")
                        + ", \"employeeId\":" + employeeId +
                        ", \"vacationId\":" + processVariables.get("vacationId")
                        + ", \"requestStatus\":\"" + processVariables.get("vacationStatus").toString() +
                        "\", \"processInstanceId\":" + task.getProcessInstanceId() +
                        ", \"assignedManagerId\":" + task.getAssignee() +
                        ", \"assignedManagerName\":\"" + processVariables.get("managerName") + "\"" +
                        ", \"lastStateManagerId\":" + processVariables.get("prevStateAssignedManagerId") +
                        ", \"lastStateManagerName\":\"" + processVariables.get("prevStateAssignedManagerName")
                        + "\"}";
            }
        }
        string += "]";
        return string;
    }

    private ProcessEngineImpl() {
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
