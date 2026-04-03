package com.demo.optimizer.analysis;

import com.demo.optimizer.model.SalesRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    @Test
    void testSortByTotalAmountDescending() {
        SalesRecord r1 = new SalesRecord("TXN-001", "2024-01-01", "Northeast", "Electronics", "Widget A", 1, 100.0, "a@test.com", null);
        SalesRecord r2 = new SalesRecord("TXN-002", "2024-01-02", "Southeast", "Clothing", "Widget B", 1, 300.0, "b@test.com", null);
        SalesRecord r3 = new SalesRecord("TXN-003", "2024-01-03", "West", "Books", "Widget C", 1, 200.0, "c@test.com", null);

        // Manually set totalAmount (normally done by DataEnricher)
        r1.setTotalAmount(100.0);
        r2.setTotalAmount(300.0);
        r3.setTotalAmount(200.0);

        Sorter sorter = new Sorter();
        List<SalesRecord> sorted = sorter.sortByTotalAmount(List.of(r1, r2, r3));

        assertEquals(300.0, sorted.get(0).getTotalAmount(), 0.001);
        assertEquals(200.0, sorted.get(1).getTotalAmount(), 0.001);
        assertEquals(100.0, sorted.get(2).getTotalAmount(), 0.001);
    }
}
