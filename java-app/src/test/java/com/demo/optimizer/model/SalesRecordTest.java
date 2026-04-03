package com.demo.optimizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalesRecordTest {

    @Test
    void testRecordConstructionAndGetters() {
        SalesRecord record = new SalesRecord(
            "TXN-00001", "2024-03-15", "Northeast", "Electronics",
            "Wireless Headphones Pro X200", 3, 89.99,
            "alice.johnson@example.com", "Express shipping requested"
        );

        assertEquals("TXN-00001", record.getId());
        assertEquals("2024-03-15", record.getDate());
        assertEquals("Northeast", record.getRegion());
        assertEquals("Electronics", record.getCategory());
        assertEquals("Wireless Headphones Pro X200", record.getProduct());
        assertEquals(3, record.getQuantity());
        assertEquals(89.99, record.getUnitPrice(), 0.001);
        assertEquals("alice.johnson@example.com", record.getCustomerEmail());
        assertEquals("Express shipping requested", record.getNotes());
    }
}
