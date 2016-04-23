package news;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.lang.model.element.Element;
//import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanatt Abrol
 */
public class XMLClass {
   
    public void createNewXML() throws URISyntaxException
    {
         try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
		
                //root element
                org.w3c.dom.Element root = doc.createElement("config");
                doc.appendChild(root);
                
		// sub root elements
		org.w3c.dom.Element wifiElement = doc.createElement("wifi");
                org.w3c.dom.Element cyberoamElement = doc.createElement("cyberoam");
                root.appendChild(wifiElement);
                root.appendChild(cyberoamElement);
                
                //child elements of wifi
                org.w3c.dom.Element ssid = doc.createElement("ssid");
                wifiElement.appendChild(ssid);
                ssid.appendChild(doc.createTextNode(""));
                org.w3c.dom.Element pass = doc.createElement("pass");
                wifiElement.appendChild(pass);
                pass.appendChild(doc.createTextNode(""));
                
                //child elements of cyberoam
                org.w3c.dom.Element user = doc.createElement("user");
                cyberoamElement.appendChild(user);
                user.appendChild(doc.createTextNode(""));
                org.w3c.dom.Element pass2 = doc.createElement("pass");
                cyberoamElement.appendChild(pass2);
                pass2.appendChild(doc.createTextNode(""));
                org.w3c.dom.Element autoAuth = doc.createElement("autoAuth");
                cyberoamElement.appendChild(autoAuth);
                autoAuth.appendChild(doc.createTextNode("0"));
                
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                FileHandle fl = new FileHandle();
                if(!fl.CheckForFolderInParentDirectory("settings"))
                    fl.CreateSubFolder(fl.getPathToRunnable(),"settings");
		StreamResult result = new StreamResult(new File(fl.getPathToRunnable()+File.separator+"settings"+File.separator+"config.xml"));

		
		transformer.transform(source, result);

		System.out.println("New file created and saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
    }
    
    public void ModifyXML(String[] values) throws ParserConfigurationException, SAXException, IOException, URISyntaxException, TransformerConfigurationException, TransformerException
    {
                FileHandle fl = new FileHandle();
                
                String filepath = fl.getPathToRunnable() + File.separator + "settings" + File.separator + "config.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
                
                //get root element
                org.w3c.dom.Node root = doc.getFirstChild();
                
                //get Child element of root
                org.w3c.dom.NodeList ChildOfRoot = root.getChildNodes();
                org.w3c.dom.Node wifiElement = ChildOfRoot.item(0);
                org.w3c.dom.Node cyberoamElement = ChildOfRoot.item(1);
                
                //handling wifi 
                org.w3c.dom.Node ssid = wifiElement.getFirstChild();
                org.w3c.dom.Node pass = wifiElement.getLastChild();
                ssid.setTextContent(values[0]);
                pass.setTextContent(values[1]);
                
                //handling cyberoam
                org.w3c.dom.Node user = cyberoamElement.getFirstChild();
                org.w3c.dom.Node pass2 = cyberoamElement.getFirstChild().getNextSibling();
                org.w3c.dom.Node autoAuth = cyberoamElement.getLastChild();
                user.setTextContent(values[2]);
                pass2.setTextContent(values[3]);
                autoAuth.setTextContent(values[4]);
                // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);

		System.out.println("File modification done");
                
                
                
    }
    public String[] ReadXML() throws SAXException, IOException, ParserConfigurationException, URISyntaxException  
    {
                
        
                FileHandle fl = new FileHandle();
                
                String filepath = fl.getPathToRunnable() + File.separator + "settings" + File.separator + "config.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
                
                //get root element
                org.w3c.dom.Node root = doc.getFirstChild();
                
                //get rootList
                org.w3c.dom.NodeList rootList = root.getChildNodes();
                
                //getting values
                String ssid = rootList.item(0).getFirstChild().getTextContent();
                String pass = rootList.item(0).getLastChild().getTextContent();
                String user = rootList.item(1).getFirstChild().getTextContent();
                String pass2 = rootList.item(1).getFirstChild().getNextSibling().getTextContent();
                String autoAuth = rootList.item(1).getLastChild().getTextContent();
                
                //adding values
                String[] values = {ssid,pass,user,pass2,autoAuth};
                
		System.out.println("File reading done");
                
        
        return values;
        
    }
   /* public static void main(String args[]) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, TransformerException 
    {
        FileHandle fl = new FileHandle();
        XMLClass xm = new XMLClass();
        EncDec ed = new EncDec();
        if(!fl.CheckForFileInParentDirectory("settings","config.xml")){
            xm.createNewXML();
            //File creation done
            String[] values = {"abc","def","ghi","jkl"};
            xm.ModifyXML(values);
            //File modification done
            String[] values2 = xm.ReadXML();
            for(int i=0; i<values2.length; i++)
                System.out.println(values[i]);
            //File reading done
        }
        
        
    }*/
}
