package com.demo.optimizer.model;

import java.util.List;

public class SalesReport {
    private List<RegionSummary> regionSummaries;
    private double grandTotal;
    private int totalTransactions;
    private String generatedAt;
    private long processingTimeMs;

    public SalesReport() {}

    public List<RegionSummary> getRegionSummaries() { return regionSummaries; }
    public void setRegionSummaries(List<RegionSummary> regionSummaries) { this.regionSummaries = regionSummaries; }

    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }

    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }

    public String getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(String generatedAt) { this.generatedAt = generatedAt; }

    public long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(long processingTimeMs) { this.processingTimeMs = processingTimeMs; }
}
