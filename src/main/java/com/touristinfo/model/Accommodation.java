package com.touristinfo.model;

public class Accommodation {
    private int id;
    private String name;
    private String type;
    private String description;
    private String location;
    private String priceRange;
    private double rating;
    private String amenities;
    private String contactInfo;
    private String websiteUrl;
    
    // Constructors
    public Accommodation() {}
    
    public Accommodation(int id, String name, String type, String description, 
                         String location, String priceRange, double rating, 
                         String amenities, String contactInfo, String websiteUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.priceRange = priceRange;
        this.rating = rating;
        this.amenities = amenities;
        this.contactInfo = contactInfo;
        this.websiteUrl = websiteUrl;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getPriceRange() { return priceRange; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
    
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
    
    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
