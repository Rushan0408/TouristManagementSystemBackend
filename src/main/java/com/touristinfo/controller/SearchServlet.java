package com.touristinfo.controller;

import com.touristinfo.model.Accommodation;
import com.touristinfo.model.TouristAttraction;
import com.touristinfo.model.Transportation;
import com.touristinfo.service.AccommodationService;
import com.touristinfo.service.TouristAttractionService;
import com.touristinfo.service.TransportationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private TouristAttractionService attractionService;
    private AccommodationService accommodationService;
    private TransportationService transportationService;
    
    public void init() {
        attractionService = new TouristAttractionService();
        accommodationService = new AccommodationService();
        transportationService = new TransportationService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category"); // attractions, accommodations, transportation, all
        String outputFormat = request.getParameter("format");
        boolean useXML = "xml".equalsIgnoreCase(outputFormat);
        
        response.setContentType(useXML ? "application/xml" : "application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if (keyword == null || keyword.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Search keyword is required");
                return;
            }
            
            // Set default category to "all" if not specified
            if (category == null || category.trim().isEmpty()) {
                category = "all";
            }
            
            // Search based on category
            if ("attractions".equalsIgnoreCase(category) || "all".equalsIgnoreCase(category)) {
                if (useXML) {
                    out.write(attractionService.searchAttractionsAsXML(keyword));
                } else {
                    List<TouristAttraction> attractions = attractionService.searchAttractions(keyword);
                    out.write("{\"attractions\": " + convertToJsonListTouristAttraction(attractions) + "}");
                }
            }
            
            if ("accommodations".equalsIgnoreCase(category) || "all".equalsIgnoreCase(category)) {
                List<Accommodation> accommodations = accommodationService.searchAccommodations(keyword);
                if (!useXML) {
                    out.write(",\"accommodations\": " + convertToJsonListAccommodation(accommodations));
                }
            }
            
            if ("transportation".equalsIgnoreCase(category) || "all".equalsIgnoreCase(category)) {
                List<Transportation> transportation = transportationService.searchTransportation(keyword);
                if (!useXML) {
                    out.write(",\"transportation\": " + convertToJson(transportation));
                }
            }
            
            if (!useXML && "all".equalsIgnoreCase(category)) {
                out.write("}");
            }
        }
    }
    
    // Helper methods for JSON conversion
    
    private String convertToJsonListTouristAttraction(List<TouristAttraction> attractions) {
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < attractions.size(); i++) {
            json.append(convertToJson(attractions.get(i)));
            if (i < attractions.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]");
        return json.toString();
    }
    
    private String convertToJson(TouristAttraction attraction) {
        return String.format(
            "{\"id\": %d, \"name\": \"%s\", \"description\": \"%s\", \"location\": \"%s\", " +
            "\"rating\": %.1f, \"visitHours\": \"%s\", \"entryFee\": %.2f, " +
            "\"contactInfo\": \"%s\", \"category\": \"%s\", \"imageUrl\": \"%s\"}",
            attraction.getId(), 
            escapeJson(attraction.getName()), 
            escapeJson(attraction.getDescription()), 
            escapeJson(attraction.getLocation()),
            attraction.getRating(), 
            escapeJson(attraction.getVisitHours()), 
            attraction.getEntryFee(),
            escapeJson(attraction.getContactInfo()), 
            escapeJson(attraction.getCategory()), 
            escapeJson(attraction.getImageUrl())
        );
    }
    
    private String convertToJsonListAccommodation(List<Accommodation> accommodations) {
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < accommodations.size(); i++) {
            json.append(convertToJson(accommodations.get(i)));
            if (i < accommodations.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]");
        return json.toString();
    }
    
    private String convertToJson(Accommodation accommodation) {
        return String.format(
            "{\"id\": %d, \"name\": \"%s\", \"type\": \"%s\", \"description\": \"%s\", " +
            "\"location\": \"%s\", \"priceRange\": \"%s\", \"rating\": %.1f, " +
            "\"amenities\": \"%s\", \"contactInfo\": \"%s\", \"websiteUrl\": \"%s\"}",
            accommodation.getId(), 
            escapeJson(accommodation.getName()), 
            escapeJson(accommodation.getType()), 
            escapeJson(accommodation.getDescription()),
            escapeJson(accommodation.getLocation()), 
            escapeJson(accommodation.getPriceRange()), 
            accommodation.getRating(),
            escapeJson(accommodation.getAmenities()), 
            escapeJson(accommodation.getContactInfo()), 
            escapeJson(accommodation.getWebsiteUrl())
        );
    }
    
    private String convertToJson(List<Transportation> transportationList) {
        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < transportationList.size(); i++) {
            json.append(convertToJson(transportationList.get(i)));
            if (i < transportationList.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]");
        return json.toString();
    }
    
    private String convertToJson(Transportation transportation) {
        return String.format(
            "{\"id\": %d, \"type\": \"%s\", \"name\": \"%s\", \"route\": \"%s\", " +
            "\"schedule\": \"%s\", \"priceInfo\": \"%s\", \"contactInfo\": \"%s\", \"notes\": \"%s\"}",
            transportation.getId(), 
            escapeJson(transportation.getType()), 
            escapeJson(transportation.getName()), 
            escapeJson(transportation.getRoute()),
            escapeJson(transportation.getSchedule()), 
            escapeJson(transportation.getPriceInfo()), 
            escapeJson(transportation.getContactInfo()),
            escapeJson(transportation.getNotes())
        );
    }
    
    // Helper method to escape JSON strings
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        
        return input.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
