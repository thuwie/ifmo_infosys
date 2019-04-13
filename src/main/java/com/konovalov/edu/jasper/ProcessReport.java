package com.konovalov.edu.jasper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ProcessReport implements Serializable {

    @NotNull @NotBlank private String processInstanceId;
    @NotNull @NotBlank private String processDescription;
    @NotNull @NotBlank private String processStatus;

    private String assignedManagerId;
    private String assignedManagerName;

    private String lastStateManagerId;
    private String lastStateManagerName;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getAssignedManagerId() {
        return assignedManagerId;
    }

    public void setAssignedManagerId(String assignedManagerId) {
        this.assignedManagerId = assignedManagerId;
    }

    public String getAssignedManagerName() {
        return assignedManagerName;
    }

    public void setAssignedManagerName(String assignedManagerName) {
        this.assignedManagerName = assignedManagerName;
    }

    public String getLastStateManagerId() {
        return lastStateManagerId;
    }

    public void setLastStateManagerId(String lastStateManagerId) {
        this.lastStateManagerId = lastStateManagerId;
    }

    public String getLastStateManagerName() {
        return lastStateManagerName;
    }

    public void setLastStateManagerName(String lastStateManagerName) {
        this.lastStateManagerName = lastStateManagerName;
    }

}
