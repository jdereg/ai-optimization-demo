package com.demo.optimizer.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCalculator {

    /**
     * INEFFICIENCY: All methods accept List<Double> (autoboxing overhead),
     * create redundant copies, and sort independently. computeAll() calls each
     * method separately — each one copies and sorts the list again instead of
     * sorting once and reusing.
     */

    public double mean(List<Double> values) {
        if (values == null || values.isEmpty()) return 0.0;
        List<Double> copy = new ArrayList<>(values);
        double sum = 0;
        for (Double v : copy) {
            sum += v;
        }
        return sum / copy.size();
    }

    public double median(List<Double> values) {
        if (values == null || values.isEmpty()) return 0.0;
        List<Double> copy = new ArrayList<>(values);
        Collections.sort(copy);
        int mid = copy.size() / 2;
        if (copy.size() % 2 == 0) {
            return (copy.get(mid - 1) + copy.get(mid)) / 2.0;
        }
        return copy.get(mid);
    }

    public double standardDeviation(List<Double> values) {
        if (values == null || values.size() < 2) return 0.0;
        List<Double> copy = new ArrayList<>(values);
        double avg = mean(copy);
        double sumSquares = 0;
        for (Double v : copy) {
            double diff = v - avg;
            sumSquares += diff * diff;
        }
        return Math.sqrt(sumSquares / (copy.size() - 1));
    }

    public double percentile(List<Double> values, int p) {
        if (values == null || values.isEmpty()) return 0.0;
        if (p < 0 || p > 100) throw new IllegalArgumentException("Percentile must be 0-100");
        List<Double> copy = new ArrayList<>(values);
        Collections.sort(copy);
        double index = (p / 100.0) * (copy.size() - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);
        if (lower == upper) {
            return copy.get(lower);
        }
        double fraction = index - lower;
        return copy.get(lower) + fraction * (copy.get(upper) - copy.get(lower));
    }

    /**
     * Compute all statistics in one call.
     * INEFFICIENCY: Calls mean(), median(), standardDeviation(), and percentile()
     * independently — each copies and sorts the list again.
     */
    public Map<String, Double> computeAll(List<Double> values) {
        Map<String, Double> stats = new HashMap<>();
        stats.put("mean", mean(values));
        stats.put("median", median(values));
        stats.put("stddev", standardDeviation(values));
        stats.put("p95", percentile(values, 95));
        stats.put("p5", percentile(values, 5));
        stats.put("min", values.isEmpty() ? 0.0 : Collections.min(new ArrayList<>(values)));
        stats.put("max", values.isEmpty() ? 0.0 : Collections.max(new ArrayList<>(values)));
        return stats;
    }
}
