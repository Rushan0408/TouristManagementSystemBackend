package com.touristinfo.service;

import com.touristinfo.dao.AccommodationDAO;
import com.touristinfo.model.Accommodation;

import java.util.List;

public class AccommodationService {
    private AccommodationDAO accommodationDAO;
    
    public AccommodationService() {
        this.accommodationDAO = new AccommodationDAO();
    }
    
    // Create a new accommodation
    public boolean createAccommodation(Accommodation accommodation) {
        return accommodationDAO.create(accommodation);
    }
    
    // Get accommodation by ID
    public Accommodation getAccommodationById(int id) {
        return accommodationDAO.getById(id);
    }
    
    // Get all accommodations
    public List<Accommodation> getAllAccommodations() {
        return accommodationDAO.getAll();
    }
    
    // Get accommodations by type
    public List<Accommodation> getAccommodationsByType(String type) {
        return accommodationDAO.getByType(type);
    }
    
    // Update an accommodation
    public boolean updateAccommodation(Accommodation accommodation) {
        return accommodationDAO.update(accommodation);
    }
    
    // Delete an accommodation
    public boolean deleteAccommodation(int id) {
        return accommodationDAO.delete(id);
    }
    
    // Search accommodations
    public List<Accommodation> searchAccommodations(String keyword) {
        return accommodationDAO.search(keyword);
    }
}