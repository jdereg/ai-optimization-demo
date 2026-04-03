package com.demo.optimizer;

import com.demo.optimizer.analysis.Aggregator;
import com.demo.optimizer.analysis.Sorter;
import com.demo.optimizer.io.DataLoader;
import com.demo.optimizer.model.RegionSummary;
import com.demo.optimizer.model.SalesRecord;
import com.demo.optimizer.model.SalesReport;
import com.demo.optimizer.report.ReportGenerator;
import com.demo.optimizer.transform.DataCleaner;
import com.demo.optimizer.transform.DataEnricher;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String[] REGIONS = {
        "Northeast", "Southeast", "Midwest", "Southwest", "West", "International"
    };

    public static void main(String[] args) throws Exception {
        String dataPath = "src/main/resources/sales-data.json";
        if (args.length > 0) {
            dataPath = args[0];
        }

        App app = new App();
        app.runPipeline(dataPath);
    }

    /**
     * Run the full data pipeline with step-by-step timing.
     * INEFFICIENCY: Loads data per-region (6 file reads) instead of loading once.
     */
    public SalesReport runPipeline(String dataPath) throws Exception {
        long pipelineStart = System.nanoTime();
        long stepStart;

        // Step 1: Load data — loads file once per region (6 times!)
        stepStart = System.nanoTime();
        DataLoader loader = new DataLoader();
        List<SalesRecord> allRecords = new ArrayList<>();
        for (String region : REGIONS) {
            List<SalesRecord> regionRecords = loader.loadByRegion(dataPath, region);
            allRecords.addAll(regionRecords);
        }
        long loadTime = (System.nanoTime() - stepStart) / 1_000_000;

        // Step 2: Clean data
        stepStart = System.nanoTime();
        DataCleaner cleaner = new DataCleaner();
        List<SalesRecord> cleanedRecords = cleaner.clean(allRecords);
        long cleanTime = (System.nanoTime() - stepStart) / 1_000_000;

        // Step 3: Enrich data
        stepStart = System.nanoTime();
        DataEnricher enricher = new DataEnricher();
        List<SalesRecord> enrichedRecords = enricher.enrich(cleanedRecords);
        long enrichTime = (System.nanoTime() - stepStart) / 1_000_000;

        // Step 4: Sort by total amount
        stepStart = System.nanoTime();
        Sorter sorter = new Sorter();
        List<SalesRecord> sortedRecords = sorter.sortByTotalAmount(enrichedRecords);
        long sortTime = (System.nanoTime() - stepStart) / 1_000_000;

        // Step 5: Aggregate by region
        stepStart = System.nanoTime();
        Aggregator aggregator = new Aggregator();
        List<RegionSummary> summaries = aggregator.aggregateByRegion(sortedRecords);
        long aggregateTime = (System.nanoTime() - stepStart) / 1_000_000;

        // Step 6: Generate report
        stepStart = System.nanoTime();
        long totalPipelineTime = (System.nanoTime() - pipelineStart) / 1_000_000;
        ReportGenerator reportGen = new ReportGenerator();
        SalesReport report = reportGen.generate(summaries, totalPipelineTime);
        String formattedReport = reportGen.formatReport(report);
        long reportTime = (System.nanoTime() - stepStart) / 1_000_000;

        totalPipelineTime = (System.nanoTime() - pipelineStart) / 1_000_000;
        report.setProcessingTimeMs(totalPipelineTime);

        // Print performance benchmark results
        System.out.println("========================================");
        System.out.println("PERFORMANCE BENCHMARK RESULTS");
        System.out.println("========================================");
        System.out.printf("Data loading:       %,6d ms%n", loadTime);
        System.out.printf("Data cleaning:      %,6d ms%n", cleanTime);
        System.out.printf("Data enrichment:    %,6d ms%n", enrichTime);
        System.out.printf("Sorting:            %,6d ms%n", sortTime);
        System.out.printf("Aggregation:        %,6d ms%n", aggregateTime);
        System.out.printf("Report generation:  %,6d ms%n", reportTime);
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL PIPELINE:     %,6d ms%n", totalPipelineTime);
        System.out.printf("Records processed:  %,6d%n", sortedRecords.size());
        System.out.println("========================================");

        return report;
    }
}
