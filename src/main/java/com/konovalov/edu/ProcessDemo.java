package com.konovalov.edu;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class ProcessDemo {
    private static volatile ProcessDemo instance;
    private static ProcessEngine processEngine;

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

    }

}
