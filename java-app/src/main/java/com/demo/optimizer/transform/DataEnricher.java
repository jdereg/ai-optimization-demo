package com.demo.optimizer.transform;

import com.demo.optimizer.model.SalesRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataEnricher {

    /**
     * Enrich records with derived fields: totalAmount, quarter, priceCategory, emailDomain.
     */
    public List<SalesRecord> enrich(List<SalesRecord> records) {
        for (SalesRecord record : records) {
            record.setTotalAmount(record.getQuantity() * record.getUnitPrice());
            record.setQuarter(computeQuarter(record.getDate()));
            record.setPriceCategory(classifyPrice(record.getUnitPrice()));

            if (record.getCustomerEmail() != null && record.getCustomerEmail().contains("@")) {
                record.setEmailDomain(record.getCustomerEmail().substring(
                    record.getCustomerEmail().indexOf('@') + 1));
            }
        }
        return records;
    }

    /**
     * Parse a date string and return the quarter label (e.g., "Q1 2024").
     * INEFFICIENCY: Creates a new DateTimeFormatter on every call
     * instead of using a static cached instance.
     */
    public String computeQuarter(String dateStr) {
        // Creates a new formatter object every time — called once per record!
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return "Q" + quarter + " " + date.getYear();
    }

    /**
     * Classify a unit price into a pricing tier.
     */
    public String classifyPrice(double unitPrice) {
        if (unitPrice < 25.0) {
            return "Budget";
        } else if (unitPrice < 100.0) {
            return "Mid-Range";
        } else {
            return "Premium";
        }
    }
}
