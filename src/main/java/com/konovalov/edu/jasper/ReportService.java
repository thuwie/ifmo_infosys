package com.konovalov.edu.jasper;

import com.konovalov.edu.commonTypes.CommonTypes;
import com.konovalov.edu.processes.ProcessEngineImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    public ReportService() {
        try {
            JasperCompileManager.compileReportToFile("C://Projects/ifmo/ifmo_infosys/src/main/resources/process_jasper_report.jrxml",
                    "C://Projects/ifmo/ifmo_infosys/src/main/resources/process_report.jasper");
        }
        catch (JRException e)
        {
            System.out.println(e.getStackTrace());
        }
    }

    public ByteArrayResource generateSimpleReport(Integer taskId) {
        try {
            ProcessReport report = new ProcessReport();

            List<Task> tasks = ProcessEngineImpl.getInstance().
                    getOrderedTaskList();

            Task task = tasks.get(taskId);
            String strTaskId = task.getId();
            Map<String, Object> processVariables = ProcessEngineImpl.getInstance().
                    getTaskService().
                    getVariables(strTaskId);

            ClassPathResource reportResource = new ClassPathResource("process_report.jasper");
            CommonTypes.requestStatus reqStatus = (CommonTypes.requestStatus)processVariables.get("vacationStatus");
            // generate parameters
            Map<String, Object> reportParameters = new HashMap<>();
            reportParameters.put(ProcessReportParams.processInstanceId, task.getProcessInstanceId());
            reportParameters.put(ProcessReportParams.processDescription, task.getName());
            reportParameters.put(ProcessReportParams.processStatus, reqStatus.getName());
            reportParameters.put(ProcessReportParams.assignedManagerId, task.getAssignee());
            reportParameters.put(ProcessReportParams.assignedManagerName, (String)processVariables.get("managerName"));
            reportParameters.put(ProcessReportParams.lastStateManagerId, processVariables.get("prevStateAssignedManagerId"));
            reportParameters.put(ProcessReportParams.lastStateManagerName,(String)processVariables.get("prevStateManagerName"));

            return exportReportToPDF(reportResource.getInputStream(), reportParameters);

        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }

    }


    private ByteArrayResource exportReportToPDF(InputStream targetStream, Map<String, Object> parameters)
    {
        try
        {
            JasperPrint jasperPrint = JasperFillManager.fillReport(targetStream, parameters, new JREmptyDataSource());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            byte[] reportContent = outputStream.toByteArray();
            return new ByteArrayResource(reportContent);
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }
    }


}
