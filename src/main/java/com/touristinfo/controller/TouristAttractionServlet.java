package com.touristinfo.controller;

import com.touristinfo.model.TouristAttraction;
import com.touristinfo.service.TouristAttractionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/attractions/*")
public class TouristAttractionServlet extends HttpServlet {
    private TouristAttractionService attractionService;

    public void init() {
        attractionService = new TouristAttractionService();
    }

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String outputFormat = request.getParameter("format");
        boolean useXML = "xml".equalsIgnoreCase(outputFormat);

        response.setContentType(useXML ? "application/xml" : "application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Get all attractions
            if (pathInfo == null || pathInfo.equals("/")) {
                if (useXML) {
                    out.write(attractionService.getAllAttractionsAsXML());
                } else {
                    List<TouristAttraction> attractions = attractionService.getAllAttractions();
                    out.write(convertToJson(attractions));
                }
            }
            // Get attraction by ID
            else if (pathInfo.startsWith("/") && pathInfo.length() > 1) {
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));

                    if (useXML) {
                        String attractionXML = attractionService.getAttractionByIdAsXML(id);
                        if (attractionXML != null) {
                            out.write(attractionXML);
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attraction not found");
                        }
                    } else {
                        TouristAttraction attraction = attractionService.getAttractionById(id);
                        if (attraction != null) {
                            out.write(convertToJson(attraction));
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attraction not found");
                        }
                    }
                } catch (NumberFormatException e) {
                    // Check if it's a category request
                    String category = pathInfo.substring(1);

                    if (useXML) {
                        out.write(attractionService.getAttractionsByCategoryAsXML(category));
                    } else {
                        List<TouristAttraction> attractions = attractionService.getAttractionsByCategory(category);
                        out.write(convertToJson(attractions));
                    }
                }
            }
        }
    }

    // Handle POST requests (create new attraction)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract attraction data from request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        double rating = Double.parseDouble(request.getParameter("rating"));
        String visitHours = request.getParameter("visitHours");
        double entryFee = Double.parseDouble(request.getParameter("entryFee"));
        String contactInfo = request.getParameter("contactInfo");
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");

        // Create attraction object
        TouristAttraction attraction = new TouristAttraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attraction.setLocation(location);
        attraction.setRating(rating);
        attraction.setVisitHours(visitHours);
        attraction.setEntryFee(entryFee);
        attraction.setContactInfo(contactInfo);
        attraction.setCategory(category);
        attraction.setImageUrl(imageUrl);

        // Save to database
        boolean success = attractionService.createAttraction(attraction);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"success\": true, \"message\": \"Attraction created successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"success\": false, \"message\": \"Failed to create attraction\"}");
            }
        }
    }

    // Handle PUT requests (update attraction)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        // Only update if ID is provided
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Attraction ID is required for update");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));

            // Check if attraction exists
            TouristAttraction existingAttraction = attractionService.getAttractionById(id);
            if (existingAttraction == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attraction not found");
                return;
            }

            // Extract updated data from request
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String ratingParam = request.getParameter("rating");
            String visitHours = request.getParameter("visitHours");
            String entryFeeParam = request.getParameter("entryFee");
            String contactInfo = request.getParameter("contactInfo");
            String category = request.getParameter("category");
            String imageUrl = request.getParameter("imageUrl");

            // Update attraction object
            if (name != null) existingAttraction.setName(name);
            if (description != null) existingAttraction.setDescription(description);
            if (location != null) existingAttraction.setLocation(location);
            if (ratingParam != null) existingAttraction.setRating(Double.parseDouble(ratingParam));
            if (visitHours != null) existingAttraction.setVisitHours(visitHours);
            if (entryFeeParam != null) existingAttraction.setEntryFee(Double.parseDouble(entryFeeParam));
            if (contactInfo != null) existingAttraction.setContactInfo(contactInfo);
            if (category != null) existingAttraction.setCategory(category);
            if (imageUrl != null) existingAttraction.setImageUrl(imageUrl);

            // Save to database
            boolean success = attractionService.updateAttraction(existingAttraction);

            // Send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try (PrintWriter out = response.getWriter()) {
                if (success) {
                    out.write("{\"success\": true, \"message\": \"Attraction updated successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("{\"success\": false, \"message\": \"Failed to update attraction\"}");
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attraction ID");
        }
    }

    // Handle DELETE requests
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        // Only delete if ID is provided
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Attraction ID is required for deletion");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));

            // Delete from database
            boolean success = attractionService.deleteAttraction(id);

            // Send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try (PrintWriter out = response.getWriter()) {
                if (success) {
                    out.write("{\"success\": true, \"message\": \"Attraction deleted successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"success\": false, \"message\": \"Attraction not found or could not be deleted\"}");
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attraction ID");
        }
    }

    // Helper method to convert attraction to JSON
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

    // Helper method to convert list of attractions to JSON
    private String convertToJson(List<TouristAttraction> attractions) {
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
