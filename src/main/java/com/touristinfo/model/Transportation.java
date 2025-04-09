package com.touristinfo.model;

public class Transportation {
    private int id;
    private String type;
    private String name;
    private String route;
    private String schedule;
    private String priceInfo;
    private String contactInfo;
    private String notes;
    
    // Constructors
    public Transportation() {}
    
    public Transportation(int id, String type, String name, String route, 
                          String schedule, String priceInfo, String contactInfo, String notes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.route = route;
        this.schedule = schedule;
        this.priceInfo = priceInfo;
        this.contactInfo = contactInfo;
        this.notes = notes;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    
    public String getPriceInfo() { return priceInfo; }
    public void setPriceInfo(String priceInfo) { this.priceInfo = priceInfo; }
    
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    @Override
    public String toString() {
        return "Transportation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
