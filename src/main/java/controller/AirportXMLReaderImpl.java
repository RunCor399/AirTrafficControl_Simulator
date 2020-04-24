package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Airport;
import model.AirportImpl;
import model.Position2DImpl;
import model.RadarPosition;
import model.RadarPositionImpl;
import model.Runway;
import model.RunwayEnd;
import model.RunwayEndImpl;
import model.RunwayImpl;
import model.Vor;
import model.VorImpl;

public class AirportXMLReaderImpl implements AirportXMLReader {
    private final String filePath;

    /**
     * Constructor of the class {@link AirportXMLReaderImpl}, which is an implementation of the interface {@link AirportXMLReader}.
     * This constructor requires the path and name of the XML file in which the {@link Airport} elements of the game are stored.
     * 
     * @param filePath the path and name of the XML file to read.
     */
    public AirportXMLReaderImpl(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Airport> getAirportListFromXML() {
        InputStream dataStream = getClass().getClassLoader().getResourceAsStream(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        List<Airport> finalList = new LinkedList<Airport>();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(dataStream);
            NodeList airports = document.getElementsByTagName("airport");
            for (int i = 0; i < airports.getLength(); i++) {
                Element airport = (Element) airports.item(i);
                finalList.add(new AirportImpl(airport.getAttribute("id"), airport.getAttribute("name"),
                        this.getVorsFromAirport(airport), this.getRunwaysFromAirport(airport),
                        this.getParkingSpotFromAirport(airport)));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return finalList;
    }

    /**
     * Gets the set of {@link Vor} of the current Airport {@link Element}.
     * 
     * @param airport the airport {@link Element} to check.
     * @return the set of {@link Vor} of the airport
     */
    private Set<Vor> getVorsFromAirport(final Element airport) {
        Set<Vor> vorSet = new HashSet<Vor>();
        NodeList vors = airport.getElementsByTagName("vor");
        for (int i = 0; i < vors.getLength(); i++) {
            Element vor = (Element) vors.item(i);
            String vorId = vor.getAttribute("id");
            double xPos = Double.parseDouble(vor.getAttribute("xPos"));
            double yPos = Double.parseDouble(vor.getAttribute("yPos"));
            vorSet.add(new VorImpl(vorId, new RadarPositionImpl(new Position2DImpl(xPos, yPos))));
        }
        return vorSet;
    }

    /**
     * Gets the list of {@link Runway} of the current Airport {@link Element}.
     * 
     * @param airport the airport {@link Element} to check.
     * @return the list of {@link Runway} of the airport
     */
    private List<Runway> getRunwaysFromAirport(final Element airport) {
        List<Runway> runwayList = new LinkedList<Runway>();
        NodeList runways = airport.getElementsByTagName("runway");
        for (int i = 0; i < runways.getLength(); i++) {
            Element runway = (Element) runways.item(i);
            NodeList ends = runway.getElementsByTagName("runwayEnd");
            Element end1 = (Element) ends.item(0);
            String endId1 = end1.getAttribute("id");
            double xPos1 = Double.parseDouble(end1.getAttribute("xPos"));
            double yPos1 = Double.parseDouble(end1.getAttribute("yPos"));
            RunwayEnd rEnd1 = new RunwayEndImpl(endId1, new RadarPositionImpl(new Position2DImpl(xPos1, yPos1)));
            Element end2 = (Element) ends.item(1);
            String endId2 = end2.getAttribute("id");
            double xPos2 = Double.parseDouble(end2.getAttribute("xPos"));
            double yPos2 = Double.parseDouble(end2.getAttribute("yPos"));
            RunwayEnd rEnd2 = new RunwayEndImpl(endId2, new RadarPositionImpl(new Position2DImpl(xPos2, yPos2)));
            runwayList.add(new RunwayImpl(rEnd1, rEnd2));
        }
        return runwayList;
    }

    /**
     * Gets the parking spot of the current Airport {@link Element}.
     * 
     * @param airport the airport {@link Element} to check.
     * @return the parking spot of the airport
     */
    private RadarPosition getParkingSpotFromAirport(final Element airport) {
        Element pSpot = (Element) airport.getElementsByTagName("parkingSpot").item(0);
        double xPosP = Double.parseDouble(pSpot.getAttribute("xPos"));
        double yPosP = Double.parseDouble(pSpot.getAttribute("yPos"));
        return new RadarPositionImpl(new Position2DImpl(xPosP, yPosP));
    }
}
