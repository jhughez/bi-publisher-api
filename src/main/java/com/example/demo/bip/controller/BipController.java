package com.example.demo.bip.controller;

import com.example.demo.bip.enums.BipReportFormat;
import com.example.demo.bip.service.BiPublisherService;
import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BipController {

    private final BiPublisherService biPublisherService;

    @Autowired
    public BipController(BiPublisherService biPublisherService) {
        this.biPublisherService = biPublisherService;
    }

    @GetMapping(value = "/htmlReport/{reportName}", produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody byte[] getHtmlReport(
        @PathVariable String reportName,
        @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(reportName, parameters, BipReportFormat.HTML);
    }

    @GetMapping(value = "/htmlReport/{schemeName}/{reportName}", produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody byte[] getHtmlReport(
        @PathVariable String schemeName,
        @PathVariable String reportName, @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(schemeName, reportName, parameters, BipReportFormat.HTML);
    }
    @GetMapping(value = "/pdfReport/{reportName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPdfReport(
        @PathVariable String reportName,
        @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(reportName, parameters, BipReportFormat.PDF);
    }

    @GetMapping(value = "/pdfReport/{schemeName}/{reportName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPdfReport(
        @PathVariable String schemeName,
        @PathVariable String reportName, @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(schemeName, reportName, parameters, BipReportFormat.PDF);
    }

    @GetMapping(value = "/excelReport/{reportName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getExcelReport(
        @PathVariable String reportName,
        @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(reportName, parameters, BipReportFormat.XLSX);
    }

    @GetMapping(value = "/excelReport/{schemeName}/{reportName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getExcelReport(
        @PathVariable String schemeName,
        @PathVariable String reportName,
        @RequestParam(required = false) Map<String, String> parameters)
        throws IOException, MessagingException {
        return biPublisherService.getBipReportAttachment(schemeName, reportName, parameters, BipReportFormat.XLSX);
    }
}
