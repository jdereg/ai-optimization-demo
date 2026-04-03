package com.demo.optimizer.analysis;

import com.demo.optimizer.model.SalesRecord;

import java.util.ArrayList;
import java.util.List;

public class Sorter {

    /**
     * Sort records by totalAmount in descending order.
     * INEFFICIENCY: Uses bubble sort O(n^2) instead of Collections.sort() O(n log n).
     * With 5,000 records this is dramatically slower.
     */
    public List<SalesRecord> sortByTotalAmount(List<SalesRecord> records) {
        List<SalesRecord> sorted = new ArrayList<>(records);
        int n = sorted.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sorted.get(j).getTotalAmount() < sorted.get(j + 1).getTotalAmount()) {
                    SalesRecord temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }

        return sorted;
    }

    /**
     * Sort records by date in ascending order.
     */
    public List<SalesRecord> sortByDate(List<SalesRecord> records) {
        List<SalesRecord> sorted = new ArrayList<>(records);
        int n = sorted.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sorted.get(j).getDate().compareTo(sorted.get(j + 1).getDate()) > 0) {
                    SalesRecord temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }

        return sorted;
    }

    /**
     * Sort records by region (ascending) then by totalAmount (descending) within each region.
     */
    public List<SalesRecord> sortByRegionThenAmount(List<SalesRecord> records) {
        List<SalesRecord> sorted = new ArrayList<>(records);
        int n = sorted.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                SalesRecord a = sorted.get(j);
                SalesRecord b = sorted.get(j + 1);
                int regionCmp = a.getRegion().compareTo(b.getRegion());
                if (regionCmp > 0 || (regionCmp == 0 && a.getTotalAmount() < b.getTotalAmount())) {
                    sorted.set(j, b);
                    sorted.set(j + 1, a);
                }
            }
        }

        return sorted;
    }
}
