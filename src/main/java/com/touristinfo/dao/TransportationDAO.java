package com.touristinfo.dao;

import com.touristinfo.model.Transportation;
import com.touristinfo.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportationDAO {
    
    // Create a new transportation
    public boolean create(Transportation transportation) {
        String sql = "INSERT INTO transportation (type, name, route, schedule, price_info, contact_info, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, transportation.getType());
            stmt.setString(2, transportation.getName());
            stmt.setString(3, transportation.getRoute());
            stmt.setString(4, transportation.getSchedule());
            stmt.setString(5, transportation.getPriceInfo());
            stmt.setString(6, transportation.getContactInfo());
            stmt.setString(7, transportation.getNotes());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get transportation by ID
    public Transportation getById(int id) {
        String sql = "SELECT * FROM transportation WHERE id = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Get all transportation options
    public List<Transportation> getAll() {
        List<Transportation> transportationList = new ArrayList<>();
        String sql = "SELECT * FROM transportation";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                transportationList.add(extractFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transportationList;
    }
    
    // Get transportation by type
    public List<Transportation> getByType(String type) {
        List<Transportation> transportationList = new ArrayList<>();
        String sql = "SELECT * FROM transportation WHERE type = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transportationList.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transportationList;
    }
    
    // Update a transportation
    public boolean update(Transportation transportation) {
        String sql = "UPDATE transportation SET type = ?, name = ?, route = ?, " +
                     "schedule = ?, price_info = ?, contact_info = ?, notes = ? " +
                     "WHERE id = ?";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, transportation.getType());
            stmt.setString(2, transportation.getName());
            stmt.setString(3, transportation.getRoute());
            stmt.setString(4, transportation.getSchedule());
            stmt.setString(5, transportation.getPriceInfo());
            stmt.setString(6, transportation.getContactInfo());
            stmt.setString(7, transportation.getNotes());
            stmt.setInt(8, transportation.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete a transportation
    public boolean delete(int id) {
        String sql = "DELETE FROM transportation WHERE id = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Search transportation
    public List<Transportation> search(String keyword) {
        List<Transportation> transportationList = new ArrayList<>();
        String sql = "SELECT * FROM transportation WHERE " +
                     "LOWER(type) LIKE LOWER(?) OR " +
                     "LOWER(name) LIKE LOWER(?) OR " +
                     "LOWER(route) LIKE LOWER(?)";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchParam = "%" + keyword + "%";
            stmt.setString(1, searchParam);
            stmt.setString(2, searchParam);
            stmt.setString(3, searchParam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transportationList.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transportationList;
    }
    
    // Helper method to extract transportation from ResultSet
    private Transportation extractFromResultSet(ResultSet rs) throws SQLException {
        Transportation transportation = new Transportation();
        transportation.setId(rs.getInt("id"));
        transportation.setType(rs.getString("type"));
        transportation.setName(rs.getString("name"));
        transportation.setRoute(rs.getString("route"));
        transportation.setSchedule(rs.getString("schedule"));
        transportation.setPriceInfo(rs.getString("price_info"));
        transportation.setContactInfo(rs.getString("contact_info"));
        transportation.setNotes(rs.getString("notes"));
        return transportation;
    }
}
