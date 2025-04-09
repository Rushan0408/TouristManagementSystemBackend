package com.touristinfo.util;

import com.touristinfo.model.TouristAttraction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
    
    // Convert TouristAttraction to XML string
    public static String touristAttractionToXML(TouristAttraction attraction) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // Root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("tourist_attraction");
            doc.appendChild(rootElement);
            
            // Add elements
            addElement(doc, rootElement, "id", String.valueOf(attraction.getId()));
            addElement(doc, rootElement, "name", attraction.getName());
            addElement(doc, rootElement, "description", attraction.getDescription());
            addElement(doc, rootElement, "location", attraction.getLocation());
            addElement(doc, rootElement, "rating", String.valueOf(attraction.getRating()));
            addElement(doc, rootElement, "visit_hours", attraction.getVisitHours());
            addElement(doc, rootElement, "entry_fee", String.valueOf(attraction.getEntryFee()));
            addElement(doc, rootElement, "contact_info", attraction.getContactInfo());
            addElement(doc, rootElement, "category", attraction.getCategory());
            addElement(doc, rootElement, "image_url", attraction.getImageUrl());
            
            // Convert to string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
            
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Convert list of attractions to XML string
    public static String touristAttractionsToXML(List<TouristAttraction> attractions) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // Root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("tourist_attractions");
            doc.appendChild(rootElement);
            
            // Add each attraction
            for (TouristAttraction attraction : attractions) {
                Element attractionElement = doc.createElement("tourist_attraction");
                rootElement.appendChild(attractionElement);
                
                addElement(doc, attractionElement, "id", String.valueOf(attraction.getId()));
                addElement(doc, attractionElement, "name", attraction.getName());
                addElement(doc, attractionElement, "description", attraction.getDescription());
                addElement(doc, attractionElement, "location", attraction.getLocation());
                addElement(doc, attractionElement, "rating", String.valueOf(attraction.getRating()));
                addElement(doc, attractionElement, "visit_hours", attraction.getVisitHours());
                addElement(doc, attractionElement, "entry_fee", String.valueOf(attraction.getEntryFee()));
                addElement(doc, attractionElement, "contact_info", attraction.getContactInfo());
                addElement(doc, attractionElement, "category", attraction.getCategory());
                addElement(doc, attractionElement, "image_url", attraction.getImageUrl());
            }
            
            // Convert to string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
            
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Helper method to add an element to a parent element
    private static void addElement(Document doc, Element parent, String name, String value) {
        Element element = doc.createElement(name);
        element.setTextContent(value != null ? value : "");
        parent.appendChild(element);
    }
    
    // Convert XML string to TouristAttraction
    public static TouristAttraction xmlToTouristAttraction(String xml) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document doc = docBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            
            NodeList attractionNodes = doc.getElementsByTagName("tourist_attraction");
            if (attractionNodes.getLength() > 0) {
                Node attractionNode = attractionNodes.item(0);
                
                if (attractionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) attractionNode;
                    
                    TouristAttraction attraction = new TouristAttraction();
                    attraction.setId(Integer.parseInt(getElementValue(element, "id")));
                    attraction.setName(getElementValue(element, "name"));
                    attraction.setDescription(getElementValue(element, "description"));
                    attraction.setLocation(getElementValue(element, "location"));
                    attraction.setRating(Double.parseDouble(getElementValue(element, "rating")));
                    attraction.setVisitHours(getElementValue(element, "visit_hours"));
                    attraction.setEntryFee(Double.parseDouble(getElementValue(element, "entry_fee")));
                    attraction.setContactInfo(getElementValue(element, "contact_info"));
                    attraction.setCategory(getElementValue(element, "category"));
                    attraction.setImageUrl(getElementValue(element, "image_url"));
                    
                    return attraction;
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Convert XML string to list of TouristAttraction
    public static List<TouristAttraction> xmlToTouristAttractions(String xml) {
        List<TouristAttraction> attractions = new ArrayList<>();
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document doc = docBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            
            NodeList attractionNodes = doc.getElementsByTagName("tourist_attraction");
            for (int i = 0; i < attractionNodes.getLength(); i++) {
                Node attractionNode = attractionNodes.item(i);
                
                if (attractionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) attractionNode;
                    
                    TouristAttraction attraction = new TouristAttraction();
                    attraction.setId(Integer.parseInt(getElementValue(element, "id")));
                    attraction.setName(getElementValue(element, "name"));
                    attraction.setDescription(getElementValue(element, "description"));
                    attraction.setLocation(getElementValue(element, "location"));
                    attraction.setRating(Double.parseDouble(getElementValue(element, "rating")));
                    attraction.setVisitHours(getElementValue(element, "visit_hours"));
                    attraction.setEntryFee(Double.parseDouble(getElementValue(element, "entry_fee")));
                    attraction.setContactInfo(getElementValue(element, "contact_info"));
                    attraction.setCategory(getElementValue(element, "category"));
                    attraction.setImageUrl(getElementValue(element, "image_url"));
                    
                    attractions.add(attraction);
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        
        return attractions;
    }
    
    // Helper method to get the value of an element
    private static String getElementValue(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node.hasChildNodes()) {
                return node.getFirstChild().getNodeValue();
            }
        }
        return "";
    }
    
    // Similar methods for Accommodation and Transportation can be added here...
}