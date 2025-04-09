package com.touristinfo.controller;

import com.touristinfo.model.Transportation;
import com.touristinfo.service.TransportationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/transportation/*")
public class TransportationServlet extends HttpServlet {
    private TransportationService transportationService;
    
    public void init() {
        transportationService = new TransportationService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // Get all transportation options
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Transportation> transportationList = transportationService.getAllTransportation();
                out.write(convertToJson(transportationList));
            } 
            // Get transportation by ID
            else if (pathInfo.startsWith("/") && pathInfo.length() > 1) {
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    Transportation transportation = transportationService.getTransportationById(id);
                    
                    if (transportation != null) {
                        out.write(convertToJson(transportation));
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transportation not found");
                    }
                } catch (NumberFormatException e) {
                    // Check if it's a type request
                    String type = pathInfo.substring(1);
                    List<Transportation> transportationList = transportationService.getTransportationByType(type);
                    out.write(convertToJson(transportationList));
                }
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Extract transportation data from request
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String route = request.getParameter("route");
        String schedule = request.getParameter("schedule");
        String priceInfo = request.getParameter("priceInfo");
        String contactInfo = request.getParameter("contactInfo");
        String notes = request.getParameter("notes");
        
        // Create transportation object
        Transportation transportation = new Transportation();
        transportation.setType(type);
        transportation.setName(name);
        transportation.setRoute(route);
        transportation.setSchedule(schedule);
        transportation.setPriceInfo(priceInfo);
        transportation.setContactInfo(contactInfo);
        transportation.setNotes(notes);
        
        // Save to database
        boolean success = transportationService.createTransportation(transportation);
        
        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.write("{\"success\": true, \"message\": \"Transportation created successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"success\": false, \"message\": \"Failed to create transportation\"}");
            }
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        // Only update if ID is provided
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transportation ID is required for update");
            return;
        }
        
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Check if transportation exists
            Transportation existingTransportation = transportationService.getTransportationById(id);
            if (existingTransportation == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transportation not found");
                return;
            }
            
            // Extract updated data from request
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String route = request.getParameter("route");
            String schedule = request.getParameter("schedule");
            String priceInfo = request.getParameter("priceInfo");
            String contactInfo = request.getParameter("contactInfo");
            String notes = request.getParameter("notes");
            
            // Update transportation object
            if (type != null) existingTransportation.setType(type);
            if (name != null) existingTransportation.setName(name);
            if (route != null) existingTransportation.setRoute(route);
            if (schedule != null) existingTransportation.setSchedule(schedule);
            if (priceInfo != null) existingTransportation.setPriceInfo(priceInfo);
            if (contactInfo != null) existingTransportation.setContactInfo(contactInfo);
            if (notes != null) existingTransportation.setNotes(notes);
            
            // Save to database
            boolean success = transportationService.updateTransportation(existingTransportation);
            
            // Send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            try (PrintWriter out = response.getWriter()) {
                if (success) {
                    out.write("{\"success\": true, \"message\": \"Transportation updated successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("{\"success\": false, \"message\": \"Failed to update transportation\"}");
                }
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transportation ID");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        // Only delete if ID is provided
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transportation ID is required for deletion");
            return;
        }
        
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Delete from database
            boolean success = transportationService.deleteTransportation(id);
            
            // Send response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            try (PrintWriter out = response.getWriter()) {
                if (success) {
                    out.write("{\"success\": true, \"message\": \"Transportation deleted successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"success\": false, \"message\": \"Transportation not found or could not be deleted\"}");
                }
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transportation ID");
        }
    }
    
    // Helper method to convert transportation to JSON
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
    
    // Helper method to convert list of transportation to JSON
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
