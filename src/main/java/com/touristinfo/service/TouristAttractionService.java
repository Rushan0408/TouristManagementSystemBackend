package com.touristinfo.service;

import com.touristinfo.dao.TouristAttractionDAO;
import com.touristinfo.model.TouristAttraction;
import com.touristinfo.util.XMLUtil;

import java.util.List;

public class TouristAttractionService {
    private TouristAttractionDAO attractionDAO;
    
    public TouristAttractionService() {
        this.attractionDAO = new TouristAttractionDAO();
    }
    
    // Create a new attraction
    public boolean createAttraction(TouristAttraction attraction) {
        return attractionDAO.create(attraction);
    }
    
    // Get attraction by ID
    public TouristAttraction getAttractionById(int id) {
        return attractionDAO.getById(id);
    }
    
    // Get attraction by ID as XML
    public String getAttractionByIdAsXML(int id) {
        TouristAttraction attraction = attractionDAO.getById(id);
        if (attraction != null) {
            return XMLUtil.touristAttractionToXML(attraction);
        }
        return null;
    }
    
    // Get all attractions
    public List<TouristAttraction> getAllAttractions() {
        return attractionDAO.getAll();
    }
    
    // Get all attractions as XML
    public String getAllAttractionsAsXML() {
        List<TouristAttraction> attractions = attractionDAO.getAll();
        return XMLUtil.touristAttractionsToXML(attractions);
    }
    
    // Get attractions by category
    public List<TouristAttraction> getAttractionsByCategory(String category) {
        return attractionDAO.getByCategory(category);
    }
    
    // Get attractions by category as XML
    public String getAttractionsByCategoryAsXML(String category) {
        List<TouristAttraction> attractions = attractionDAO.getByCategory(category);
        return XMLUtil.touristAttractionsToXML(attractions);
    }
    
    // Update an attraction
    public boolean updateAttraction(TouristAttraction attraction) {
        return attractionDAO.update(attraction);
    }
    
    // Delete an attraction
    public boolean deleteAttraction(int id) {
        return attractionDAO.delete(id);
    }
    
    // Search attractions
    public List<TouristAttraction> searchAttractions(String keyword) {
        return attractionDAO.search(keyword);
    }
    
    // Search attractions as XML
    public String searchAttractionsAsXML(String keyword) {
        List<TouristAttraction> attractions = attractionDAO.search(keyword);
        return XMLUtil.touristAttractionsToXML(attractions);
    }
}