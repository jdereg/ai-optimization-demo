package com.demo.optimizer.io;

import com.demo.optimizer.model.SalesRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Load all sales records from a JSON file.
     */
    public List<SalesRecord> loadFromFile(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<List<SalesRecord>>() {});
    }

    /**
     * Load sales records filtered by region.
     * INEFFICIENCY: Re-reads and re-parses the entire file for each call
     * instead of filtering an already-loaded list.
     */
    public List<SalesRecord> loadByRegion(String path, String region) throws IOException {
        List<SalesRecord> all = loadFromFile(path);  // Full file read + parse each time!
        List<SalesRecord> filtered = new ArrayList<>();
        for (SalesRecord record : all) {
            if (region.equals(record.getRegion())) {
                filtered.add(record);
            }
        }
        return filtered;
    }
}
