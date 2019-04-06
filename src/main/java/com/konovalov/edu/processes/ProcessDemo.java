package com.konovalov.edu.processes;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

// TODO(ipolyakov): Add TaskService & RuntimeService
// TODO(ipolyakov): Replace in appropriate place

public class ProcessDemo {
    private static volatile ProcessDemo instance;
    public final ProcessEngine processEngine;

//    private final RuntimeService runtimeService;
//    private final TaskService taskService;

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
    }

}
