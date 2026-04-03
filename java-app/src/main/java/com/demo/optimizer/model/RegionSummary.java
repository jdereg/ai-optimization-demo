package com.demo.optimizer.model;

import java.util.Map;

public class RegionSummary {
    private String region;
    private int transactionCount;
    private double totalRevenue;
    private double averageOrderValue;
    private double medianOrderValue;
    private double standardDeviation;
    private double p95OrderValue;
    private String topProduct;
    private Map<String, Double> categoryBreakdown;

    public RegionSummary() {}

    public RegionSummary(String region) {
        this.region = region;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public double getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(double averageOrderValue) { this.averageOrderValue = averageOrderValue; }

    public double getMedianOrderValue() { return medianOrderValue; }
    public void setMedianOrderValue(double medianOrderValue) { this.medianOrderValue = medianOrderValue; }

    public double getStandardDeviation() { return standardDeviation; }
    public void setStandardDeviation(double standardDeviation) { this.standardDeviation = standardDeviation; }

    public double getP95OrderValue() { return p95OrderValue; }
    public void setP95OrderValue(double p95OrderValue) { this.p95OrderValue = p95OrderValue; }

    public String getTopProduct() { return topProduct; }
    public void setTopProduct(String topProduct) { this.topProduct = topProduct; }

    public Map<String, Double> getCategoryBreakdown() { return categoryBreakdown; }
    public void setCategoryBreakdown(Map<String, Double> categoryBreakdown) { this.categoryBreakdown = categoryBreakdown; }
}
