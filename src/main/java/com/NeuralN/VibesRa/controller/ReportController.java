package com.NeuralN.VibesRa.controller;

import com.NeuralN.VibesRa.model.Report;
import com.NeuralN.VibesRa.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable int reportId) {
        Report report = reportService.getReportById(reportId);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Report> saveReport(@RequestBody Report report) {
        return new ResponseEntity<>(reportService.saveReport(report), HttpStatus.CREATED);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable int reportId) {
        reportService.deleteReport(reportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
