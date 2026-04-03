package com.demo.optimizer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesRecord {
    private String id;
    private String date;
    private String region;
    private String category;
    private String product;
    private int quantity;
    private double unitPrice;
    private String customerEmail;
    private String notes;

    // Derived fields (set by DataEnricher)
    private double totalAmount;
    private String quarter;
    private String priceCategory;
    private String emailDomain;

    public SalesRecord() {}

    public SalesRecord(String id, String date, String region, String category,
                       String product, int quantity, double unitPrice,
                       String customerEmail, String notes) {
        this.id = id;
        this.date = date;
        this.region = region;
        this.category = category;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.customerEmail = customerEmail;
        this.notes = notes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }

    public String getPriceCategory() { return priceCategory; }
    public void setPriceCategory(String priceCategory) { this.priceCategory = priceCategory; }

    public String getEmailDomain() { return emailDomain; }
    public void setEmailDomain(String emailDomain) { this.emailDomain = emailDomain; }
}
