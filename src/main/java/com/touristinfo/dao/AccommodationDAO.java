package com.touristinfo.dao;

import com.touristinfo.model.Accommodation;
import com.touristinfo.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccommodationDAO {
    
    // Create a new accommodation
    public boolean create(Accommodation accommodation) {
        String sql = "INSERT INTO accommodations (name, type, description, location, price_range, rating, amenities, contact_info, website_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, accommodation.getName());
            stmt.setString(2, accommodation.getType());
            stmt.setString(3, accommodation.getDescription());
            stmt.setString(4, accommodation.getLocation());
            stmt.setString(5, accommodation.getPriceRange());
            stmt.setDouble(6, accommodation.getRating());
            stmt.setString(7, accommodation.getAmenities());
            stmt.setString(8, accommodation.getContactInfo());
            stmt.setString(9, accommodation.getWebsiteUrl());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get accommodation by ID
    public Accommodation getById(int id) {
        String sql = "SELECT * FROM accommodations WHERE id = ?";
        
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
    
    // Get all accommodations
    public List<Accommodation> getAll() {
        List<Accommodation> accommodations = new ArrayList<>();
        String sql = "SELECT * FROM accommodations";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                accommodations.add(extractFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accommodations;
    }
    
    // Get accommodations by type
    public List<Accommodation> getByType(String type) {
        List<Accommodation> accommodations = new ArrayList<>();
        String sql = "SELECT * FROM accommodations WHERE type = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accommodations.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accommodations;
    }
    
    // Update an accommodation
    public boolean update(Accommodation accommodation) {
        String sql = "UPDATE accommodations SET name = ?, type = ?, description = ?, location = ?, " +
                     "price_range = ?, rating = ?, amenities = ?, contact_info = ?, website_url = ? " +
                     "WHERE id = ?";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, accommodation.getName());
            stmt.setString(2, accommodation.getType());
            stmt.setString(3, accommodation.getDescription());
            stmt.setString(4, accommodation.getLocation());
            stmt.setString(5, accommodation.getPriceRange());
            stmt.setDouble(6, accommodation.getRating());
            stmt.setString(7, accommodation.getAmenities());
            stmt.setString(8, accommodation.getContactInfo());
            stmt.setString(9, accommodation.getWebsiteUrl());
            stmt.setInt(10, accommodation.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete an accommodation
    public boolean delete(int id) {
        String sql = "DELETE FROM accommodations WHERE id = ?";
        
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
    
    // Search accommodations
    public List<Accommodation> search(String keyword) {
        List<Accommodation> accommodations = new ArrayList<>();
        String sql = "SELECT * FROM accommodations WHERE " +
                     "LOWER(name) LIKE LOWER(?) OR " +
                     "LOWER(type) LIKE LOWER(?) OR " +
                     "LOWER(description) LIKE LOWER(?) OR " +
                     "LOWER(location) LIKE LOWER(?)";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchParam = "%" + keyword + "%";
            stmt.setString(1, searchParam);
            stmt.setString(2, searchParam);
            stmt.setString(3, searchParam);
            stmt.setString(4, searchParam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accommodations.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accommodations;
    }
    
    // Helper method to extract accommodation from ResultSet
    private Accommodation extractFromResultSet(ResultSet rs) throws SQLException {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(rs.getInt("id"));
        accommodation.setName(rs.getString("name"));
        accommodation.setType(rs.getString("type"));
        accommodation.setDescription(rs.getString("description"));
        accommodation.setLocation(rs.getString("location"));
        accommodation.setPriceRange(rs.getString("price_range"));
        accommodation.setRating(rs.getDouble("rating"));
        accommodation.setAmenities(rs.getString("amenities"));
        accommodation.setContactInfo(rs.getString("contact_info"));
        accommodation.setWebsiteUrl(rs.getString("website_url"));
        return accommodation;
    }
}
