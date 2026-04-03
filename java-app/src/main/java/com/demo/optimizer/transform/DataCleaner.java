package com.demo.optimizer.transform;

import com.demo.optimizer.model.SalesRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataCleaner {

    /**
     * Clean the list of records: validate emails, normalize notes, remove invalid entries.
     */
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

    /**
     * Validate email format using regex.
     * INEFFICIENCY: Compiles the regex pattern on every single call
     * instead of using a static precompiled Pattern.
     */
    public boolean isValidEmail(String email) {
        // Recompiles this pattern every time — called once per record!
        Pattern pattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );
        return pattern.matcher(email).matches();
    }

    /**
     * Normalize notes: trim whitespace, collapse multiple spaces, remove non-printable chars.
     * INEFFICIENCY: Builds the result string using += in a character loop
     * instead of using StringBuilder.
     */
    public String normalizeNotes(String notes) {
        if (notes == null) return null;

        String result = "";
        boolean lastWasSpace = false;

        for (int i = 0; i < notes.length(); i++) {
            char c = notes.charAt(i);

            // Skip non-printable characters
            if (c < 32 && c != '\n' && c != '\t') {
                continue;
            }

            // Collapse whitespace
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result += ' ';  // String concatenation in a loop!
                    lastWasSpace = true;
                }
            } else {
                result += c;  // String concatenation in a loop!
                lastWasSpace = false;
            }
        }

        return result.trim();
    }
}
