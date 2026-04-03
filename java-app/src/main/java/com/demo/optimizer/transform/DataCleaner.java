package com.demo.optimizer.transform;

import com.demo.optimizer.model.SalesRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataCleaner {

    public List<SalesRecord> clean(List<SalesRecord> records) {
        List<SalesRecord> cleaned = new ArrayList<>();
        for (SalesRecord record : records) {
            if (record.getId() == null || record.getId().isBlank()) {
                continue;
            }
            if (record.getCustomerEmail() != null && !isValidEmail(record.getCustomerEmail())) {
                continue;
            }
            if (record.getQuantity() <= 0 || record.getUnitPrice() <= 0) {
                continue;
            }
            if (record.getNotes() != null) {
                record.setNotes(normalizeNotes(record.getNotes()));
            }
            cleaned.add(record);
        }
        return cleaned;
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );
        return pattern.matcher(email).matches();
    }

    public String normalizeNotes(String notes) {
        if (notes == null) return null;

        String result = "";
        boolean lastWasSpace = false;

        for (int i = 0; i < notes.length(); i++) {
            char c = notes.charAt(i);

            if (c < 32 && c != '\n' && c != '\t') {
                continue;
            }

            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result += ' ';
                    lastWasSpace = true;
                }
            } else {
                result += c;
                lastWasSpace = false;
            }
        }

        return result.trim();
    }
}
