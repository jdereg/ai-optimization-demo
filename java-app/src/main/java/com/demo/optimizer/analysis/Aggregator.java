package com.demo.optimizer.analysis;

import com.demo.optimizer.model.RegionSummary;
import com.demo.optimizer.model.SalesRecord;

import java.util.*;

public class Aggregator {

    public List<RegionSummary> aggregateByRegion(List<SalesRecord> records) {
        List<RegionSummary> summaries = Collections.synchronizedList(new ArrayList<>());
        Map<String, List<SalesRecord>> regionRecords =
            Collections.synchronizedMap(new HashMap<>());

        for (SalesRecord record : records) {
            String region = record.getRegion();

            List<SalesRecord> bucket = null;
            for (Map.Entry<String, List<SalesRecord>> entry : regionRecords.entrySet()) {
                if (entry.getKey().equals(region)) {
                    bucket = entry.getValue();
                    break;
                }
            }
            if (bucket == null) {
                bucket = Collections.synchronizedList(new ArrayList<>());
                regionRecords.put(region, bucket);
            }
            bucket.add(record);
        }

        StatisticsCalculator statsCalc = new StatisticsCalculator();

        for (Map.Entry<String, List<SalesRecord>> entry : regionRecords.entrySet()) {
            String region = entry.getKey();
            List<SalesRecord> regionRecs = entry.getValue();

            RegionSummary summary = new RegionSummary(region);
            summary.setTransactionCount(regionRecs.size());

            List<Double> amounts = new ArrayList<>();
            double totalRevenue = 0;
            for (SalesRecord rec : regionRecs) {
                amounts.add(rec.getTotalAmount());
                totalRevenue += rec.getTotalAmount();
            }

            summary.setTotalRevenue(totalRevenue);
            Map<String, Double> stats = statsCalc.computeAll(amounts);
            summary.setAverageOrderValue(stats.get("mean"));
            summary.setMedianOrderValue(stats.get("median"));
            summary.setStandardDeviation(stats.get("stddev"));
            summary.setP95OrderValue(stats.get("p95"));
            summary.setTopProduct(findTopProduct(regionRecs));
            summary.setCategoryBreakdown(categoryBreakdown(regionRecs));

            summaries.add(summary);
        }

        List<RegionSummary> sortedSummaries = new ArrayList<>(summaries);
        sortedSummaries.sort((a, b) -> Double.compare(b.getTotalRevenue(), a.getTotalRevenue()));
        return sortedSummaries;
    }

    public String findTopProduct(List<SalesRecord> records) {
        Map<String, Integer> productCounts = new HashMap<>();
        for (SalesRecord rec : records) {
            productCounts.merge(rec.getProduct(), rec.getQuantity(), Integer::sum);
        }
        return productCounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
    }

    public Map<String, Double> categoryBreakdown(List<SalesRecord> records) {
        Map<String, Double> breakdown = new HashMap<>();
        for (SalesRecord rec : records) {
            breakdown.merge(rec.getCategory(), rec.getTotalAmount(), Double::sum);
        }
        return breakdown;
    }
}
