package com.konovalov.edu;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class ProcessDemo {
    private static ProcessDemo processDemoInstance;

    private static ProcessEngine processEngine;

    public static synchronized ProcessDemo getInstance() {
        if (processDemoInstance == null) {
            processDemoInstance = new ProcessDemo();
        }
        return processDemoInstance;
    }

    private ProcessDemo() {
        // just creates resource table for flowable
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:postgresql://rc1a-2rwkp00qc32pm8t8.mdb.yandexcloud.net:6432/infosys")
                .setJdbcUsername("sysuser")
                .setJdbcPassword("myverypassword")
                .setJdbcDriver("org.postgresql.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        processEngine = cfg.buildProcessEngine();

    }

}
