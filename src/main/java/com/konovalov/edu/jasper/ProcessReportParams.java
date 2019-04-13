package com.konovalov.edu.jasper;

import org.springframework.context.annotation.Description;

public final class ProcessReportParams {

    public static String processInstanceId = "PROCESS_INSTANCE_ID";
    public static String processDescription = "PROCESS_DESCRIPTION";
    public static String processStatus = "PROCESS_STATUS";
    public static String assignedManagerId = "ASSIGNED_MANAGER_ID";
    public static String assignedManagerName = "ASSIGNED_MANAGER_NAME";
    public static String lastStateManagerId = "LAST_STATE_MANAGER_ID";
    public static String lastStateManagerName = "LAST_STATE_MANAGER_NAME";

    private ProcessReportParams() {}
}
