package parks;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
public class DistancesXMLParser {
	
	ParkList parks = null;
	List<String> files = new ArrayList<String> ();
	
	public DistancesXMLParser(ParkList parks) {
		this.parks = parks;
		files.add("yellowstone.xml");
		files.add("sequoia.xml");
		files.add("yosemite.xml");
		files.add("mtrainier.xml");
		files.add("craterlake.xml");
		files.add("windcave.xml");
		files.add("mesaverde.xml");
		files.add("glacier.xml");
		files.add("rockymtn.xml");
		files.add("lassen.xml");
		files.add("acadia.xml");
		files.add("grandcanyon.xml");
		files.add("zion.xml");
		files.add("hotsprings.xml");
		files.add("bryce.xml");
		files.add("grandteton.xml");
		files.add("carlsbad.xml");
		files.add("everglades.xml");
		files.add("greatsmoky.xml");
		files.add("shenandoah.xml");
		files.add("olympic.xml");
		files.add("kingscanyon.xml");
		files.add("isleroyale.xml");
		files.add("mammothcave.xml");
		files.add("bigbend.xml");
		files.add("petrified.xml");
		files.add("canyonlands.xml");
		files.add("guadalupe.xml");
		files.add("northcascades.xml");
		files.add("redwood.xml");
		files.add("voyageurs.xml");
		files.add("arches.xml");
		files.add("capitolreef.xml");
		files.add("badlands.xml");
		files.add("teddyr.xml");
		files.add("biscayne.xml");
		files.add("greatbasin.xml");
		files.add("saguaro.xml");
		files.add("deathvalley.xml");
		files.add("joshuatree.xml");
		files.add("blackcanyon.xml");
		files.add("cuyahoga.xml");
		files.add("congaree.xml");
		files.add("greatsanddunes.xml");
		files.add("pinnacles.xml");
		//files.add(".xml");
	}
	

	public void parseFiles() {
		try {
			Iterator<String> iter = files.iterator();
			Integer thisParkID = new Integer(0);
			while (iter.hasNext()) {
				File inputFile = new File("parkDistData/" + iter.next());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile);
				doc.getDocumentElement().normalize();
				//more stuff..         
				NodeList nList = doc.getElementsByTagName("distance");
		         //System.out.println("----------------------------");
		         
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		                Element eElement = (Element) nNode;
		                Integer distanceInM = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());
		                Integer distanceInK = distanceInM/1000;
		                //debug
		                //System.out.println("Adding " + thisParkID + " " + temp + " " + distanceInK);
		                parks.addDistance(thisParkID, new Integer(temp), distanceInK);
		            }
		         }
				//get distances and add to parks list...
  			
				thisParkID++;
			}
  		/*
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("student");
         System.out.println("----------------------------");
         
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Student roll no : " 
                  + eElement.getAttribute("rollno"));
               System.out.println("First Name : " 
                  + eElement
                  .getElementsByTagName("firstname")
                  .item(0)
                  .getTextContent());
               System.out.println("Last Name : " 
                  + eElement
                  .getElementsByTagName("lastname")
                  .item(0)
                  .getTextContent());
               System.out.println("Nick Name : " 
                  + eElement
                  .getElementsByTagName("nickname")
                  .item(0)
                  .getTextContent());
               System.out.println("Marks : " 
                  + eElement
                  .getElementsByTagName("marks")
                  .item(0)
                  .getTextContent());
            
         }*/
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
