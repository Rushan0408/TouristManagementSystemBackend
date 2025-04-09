package com.touristinfo.service;

import com.touristinfo.dao.TransportationDAO;
import com.touristinfo.model.Transportation;

import java.util.List;

public class TransportationService {
    private TransportationDAO transportationDAO;
    
    public TransportationService() {
        this.transportationDAO = new TransportationDAO();
    }
    
    // Create a new transportation
    public boolean createTransportation(Transportation transportation) {
        return transportationDAO.create(transportation);
    }
    
    // Get transportation by ID
    public Transportation getTransportationById(int id) {
        return transportationDAO.getById(id);
    }
    
    // Get all transportation options
    public List<Transportation> getAllTransportation() {
        return transportationDAO.getAll();
    }
    
    // Get transportation by type
    public List<Transportation> getTransportationByType(String type) {
        return transportationDAO.getByType(type);
    }
    
    // Update a transportation
    public boolean updateTransportation(Transportation transportation) {
        return transportationDAO.update(transportation);
    }
    
    // Delete a transportation
    public boolean deleteTransportation(int id) {
        return transportationDAO.delete(id);
    }
    
    // Search transportation
    public List<Transportation> searchTransportation(String keyword) {
        return transportationDAO.search(keyword);
    }
}