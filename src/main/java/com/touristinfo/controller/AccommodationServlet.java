package com.touristinfo.controller;

import com.touristinfo.model.Accommodation;
import com.touristinfo.service.AccommodationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/accommodations/*")
public class AccommodationServlet extends HttpServlet {
    private AccommodationService accommodationService;

    public void init() {
        accommodationService = new AccommodationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Get all accommodations
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Accommodation> accommodations = accommodationService.getAllAccommodations();
                out.write(convertToJson(accommodations));
            }
            // Get accommodation by ID
            else if (pathInfo.startsWith("/") && pathInfo.length() > 1) {
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    Accommodation accommodation = accommodationService.getAccommodationById(id);

                    if (accommodation != null) {
                        out.write(convertToJson(accommodation));
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Accommodation not found");
                    }
                } catch (NumberFormatException e) {
                    // Check if it's a type request
                    String type = pathInfo.substring(1);
                    List<Accommodation> accommodations = accommodationService.getAccommodationsByType(type);
                    out.write(convertToJson(accommodations));
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Extract accommodation data from request
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String priceRange = request.getParameter("priceRange");
        double rating = Double.parseDouble(request.getParameter("rating"));
        String amenities = request.getParameter("amenities");
        String contactInfo = request.getParameter("contactInfo");
        String websiteUrl = request.getParameter("websiteUrl");

        // Create accommodation object
        Accommodation accommodation = new Accommodation();
        accommodation.setName(name);
        accommodation.setType(type);
        accommodation.setDescription(description);
        accommodation.setLocation(location);
        accommodation.setPriceRange(priceRange);
        accommodation.setRating(rating);
        accommodation.setAmenities(amenities);
        accommodation.setContactInfo(contactInfo);
        accommodation.setWebsiteUrl(websiteUrl);

        // Save to database
        boolean success = accommodationService.createAccommodation(accommodation);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"success\": true, \"message\": \"Accommodation created successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"success\": false, \"message\": \"Failed to create accommodation\"}");
            }
        }
    }

    // Helper method to convert accommodation to JSON
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

    // Helper method to convert list of accommodations to JSON
    private String convertToJson(List<Accommodation> accommodations) {
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
