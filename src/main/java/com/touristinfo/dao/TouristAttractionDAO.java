package com.touristinfo.dao;

import com.touristinfo.model.TouristAttraction;
import com.touristinfo.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TouristAttractionDAO {
    
    // Create a new tourist attraction
    public boolean create(TouristAttraction attraction) {
        String sql = "INSERT INTO tourist_attractions (name, description, location, rating, visit_hours, entry_fee, contact_info, category, image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, attraction.getName());
            stmt.setString(2, attraction.getDescription());
            stmt.setString(3, attraction.getLocation());
            stmt.setDouble(4, attraction.getRating());
            stmt.setString(5, attraction.getVisitHours());
            stmt.setDouble(6, attraction.getEntryFee());
            stmt.setString(7, attraction.getContactInfo());
            stmt.setString(8, attraction.getCategory());
            stmt.setString(9, attraction.getImageUrl());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch ( SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get attraction by ID
    public TouristAttraction getById(int id) {
        String sql = "SELECT * FROM tourist_attractions WHERE id = ?";
        
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
    
    // Get all attractions
    public List<TouristAttraction> getAll() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM tourist_attractions";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                attractions.add(extractFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attractions;
    }
    
    // Get attractions by category
    public List<TouristAttraction> getByCategory(String category) {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM tourist_attractions WHERE category = ?";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attractions.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attractions;
    }
    
    // Update an attraction
    public boolean update(TouristAttraction attraction) {
        String sql = "UPDATE tourist_attractions SET name = ?, description = ?, location = ?, " +
                     "rating = ?, visit_hours = ?, entry_fee = ?, contact_info = ?, category = ?, image_url = ? " +
                     "WHERE id = ?";
                     
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, attraction.getName());
            stmt.setString(2, attraction.getDescription());
            stmt.setString(3, attraction.getLocation());
            stmt.setDouble(4, attraction.getRating());
            stmt.setString(5, attraction.getVisitHours());
            stmt.setDouble(6, attraction.getEntryFee());
            stmt.setString(7, attraction.getContactInfo());
            stmt.setString(8, attraction.getCategory());
            stmt.setString(9, attraction.getImageUrl());
            stmt.setInt(10, attraction.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete an attraction
    public boolean delete(int id) {
        String sql = "DELETE FROM tourist_attractions WHERE id = ?";
        
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
    
    // Search attractions
    public List<TouristAttraction> search(String keyword) {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM tourist_attractions WHERE " +
                     "LOWER(name) LIKE LOWER(?) OR " +
                     "LOWER(description) LIKE LOWER(?) OR " +
                     "LOWER(location) LIKE LOWER(?) OR " +
                     "LOWER(category) LIKE LOWER(?)";
        
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchParam = "%" + keyword + "%";
            stmt.setString(1, searchParam);
            stmt.setString(2, searchParam);
            stmt.setString(3, searchParam);
            stmt.setString(4, searchParam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attractions.add(extractFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return attractions;
    }
    
    // Helper method to extract attraction from ResultSet
    private TouristAttraction extractFromResultSet(ResultSet rs) throws SQLException {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setId(rs.getInt("id"));
        attraction.setName(rs.getString("name"));
        attraction.setDescription(rs.getString("description"));
        attraction.setLocation(rs.getString("location"));
        attraction.setRating(rs.getDouble("rating"));
        attraction.setVisitHours(rs.getString("visit_hours"));
        attraction.setEntryFee(rs.getDouble("entry_fee"));
        attraction.setContactInfo(rs.getString("contact_info"));
        attraction.setCategory(rs.getString("category"));
        attraction.setImageUrl(rs.getString("image_url"));
        return attraction;
    }
}
