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

    public List<SalesRecord> loadFromFile(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<List<SalesRecord>>() {});
    }

    public List<SalesRecord> loadByRegion(String path, String region) throws IOException {
        List<SalesRecord> all = loadFromFile(path);
        List<SalesRecord> filtered = new ArrayList<>();
        for (SalesRecord record : all) {
            if (region.equals(record.getRegion())) {
                filtered.add(record);
            }
        }
        return filtered;
    }
}
