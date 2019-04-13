package com.konovalov.edu.rest;

import com.konovalov.edu.jasper.ReportService;
import com.konovalov.edu.processes.ProcessEngineImpl;
import org.flowable.task.api.Task;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processes/")
public class ReportsController {

    private HttpHeaders responseHeaders;

    private ReportService reportService;

    public ReportsController(ReportService reportService) {
        this.reportService = reportService;

        this.responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/pdf");
    }

    @GetMapping(value = "/report/get/{taskId}")
    public ResponseEntity<ByteArrayResource> generateSimplePDFReport(@PathVariable("taskId") Integer indexTaskId) {
        ByteArrayResource byteArrayResource = reportService.generateSimpleReport(indexTaskId);
        return new ResponseEntity<>(byteArrayResource, responseHeaders, HttpStatus.OK);
    }
}
