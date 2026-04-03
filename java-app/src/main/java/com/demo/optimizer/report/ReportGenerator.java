package com.demo.optimizer.report;

import com.demo.optimizer.model.RegionSummary;
import com.demo.optimizer.model.SalesReport;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ReportGenerator {

    /**
     * Build a SalesReport from region summaries.
     */
    public SalesReport generate(List<RegionSummary> summaries, long processingTimeMs) {
        SalesReport report = new SalesReport();
        report.setRegionSummaries(summaries);
        report.setProcessingTimeMs(processingTimeMs);
        report.setGeneratedAt(Instant.now().toString());

        double grandTotal = 0;
        int totalTransactions = 0;
        for (RegionSummary summary : summaries) {
            grandTotal += summary.getTotalRevenue();
            totalTransactions += summary.getTransactionCount();
        }
        report.setGrandTotal(grandTotal);
        report.setTotalTransactions(totalTransactions);

        return report;
    }

    /**
     * Format a SalesReport as a human-readable text summary.
     * INEFFICIENCY: Builds the output string using += in a loop
     * instead of StringBuilder.
     */
    public String formatReport(SalesReport report) {
        String output = "";

        output += "=== SALES ANALYSIS REPORT ===\n";
        output += "Generated: " + report.getGeneratedAt() + "\n";
        output += "Total Transactions: " + report.getTotalTransactions() + "\n";
        output += String.format("Grand Total Revenue: $%,.2f\n", report.getGrandTotal());
        output += "Processing Time: " + report.getProcessingTimeMs() + " ms\n";
        output += "\n";

        for (RegionSummary summary : report.getRegionSummaries()) {
            output += "--- " + summary.getRegion() + " ---\n";
            output += "  Transactions: " + summary.getTransactionCount() + "\n";
            output += String.format("  Total Revenue: $%,.2f\n", summary.getTotalRevenue());
            output += String.format("  Avg Order Value: $%,.2f\n", summary.getAverageOrderValue());
            output += String.format("  Median Order: $%,.2f\n", summary.getMedianOrderValue());
            output += String.format("  Std Deviation: $%,.2f\n", summary.getStandardDeviation());
            output += String.format("  95th Percentile: $%,.2f\n", summary.getP95OrderValue());
            output += "  Top Product: " + summary.getTopProduct() + "\n";

            if (summary.getCategoryBreakdown() != null) {
                output += "  Category Breakdown:\n";
                for (Map.Entry<String, Double> entry : summary.getCategoryBreakdown().entrySet()) {
                    output += String.format("    %s: $%,.2f\n", entry.getKey(), entry.getValue());
                }
            }
            output += "\n";
        }

        return output;
    }
}
