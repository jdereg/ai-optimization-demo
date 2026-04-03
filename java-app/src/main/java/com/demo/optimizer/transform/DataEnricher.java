package com.demo.optimizer.transform;

import com.demo.optimizer.model.SalesRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataEnricher {

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

    public String computeQuarter(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return "Q" + quarter + " " + date.getYear();
    }

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
