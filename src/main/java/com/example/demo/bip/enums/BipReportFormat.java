package com.example.demo.bip.enums;

public enum BipReportFormat {
    HTML("html"),
    PDF("pdf"),
    XLSX("xlsx");

    private final String format;

    BipReportFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
