package com.NeuralN.VibesRa.service;

import com.NeuralN.VibesRa.model.Report;
import com.NeuralN.VibesRa.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(int reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        return report.orElse(null);
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReport(int reportId) {
        reportRepository.deleteById(reportId);
    }
}
