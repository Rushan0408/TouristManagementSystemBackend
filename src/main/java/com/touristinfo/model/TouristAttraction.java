package com.touristinfo.model;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private String location;
    private double rating;
    private String visitHours;
    private double entryFee;
    private String contactInfo;
    private String category;
    private String imageUrl;
    
    // Constructors
    public TouristAttraction() {}
    
    public TouristAttraction(int id, String name, String description, String location, 
                             double rating, String visitHours, double entryFee, 
                             String contactInfo, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.rating = rating;
        this.visitHours = visitHours;
        this.entryFee = entryFee;
        this.contactInfo = contactInfo;
        this.category = category;
        this.imageUrl = imageUrl;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public String getVisitHours() { return visitHours; }
    public void setVisitHours(String visitHours) { this.visitHours = visitHours; }
    
    public double getEntryFee() { return entryFee; }
    public void setEntryFee(double entryFee) { this.entryFee = entryFee; }
    
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    @Override
    public String toString() {
        return "TouristAttraction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

