package co.tyec.warDeploymentExamples

import static org.testng.Assert.*
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import org.testng.annotations.Test
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NamedNodeMap
import org.xml.sax.InputSource
import org.apache.commons.cli.ParseException;

import java.beans.XMLDecoder;
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

public class AddUserToTomcat {

    public static void main (args) {
        def cli = new CliBuilder(usage: "groovy AddUserToTomcat.groovy -f \"C:\\Program Files\\Apache Software Foundation\\Tomcat 7\\conf\\tomcat-users.xml\"")
        cli.f(longOpt: 'file', 'Tomcat Users file', required: true, args: 1)
        cli.h('Help/Usage')
        def options = cli.parse(args)
        if(!options || options.h) {
            //    cli.usage();
            return
        }

        // find tomcat-users.xml file
        // readxml
        String inputFilePath = options.f.trim();
        File inputFile = new File(inputFilePath)
        def inputXmlString = inputFile.getText();

        // add user
        def outputXmlString = addManagerUser(inputXmlString);

        // write xml
        inputFile.setText(outputXmlString);
        println "Writing to " + inputFilePath
    }

    static def addManagerUser(String input) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = loadXMLFromString(input);

        XPathFactory xpathFact = XPathFactory.newInstance();
        XPath xpath = xpathFact.newXPath();

        boolean found = containsRolesManagerScript(doc);

        if(!found) {
            Element user = doc.createElement("user");
            user.setAttribute("username", "admin");
            user.setAttribute("roles", "manager-gui,manager-script");
            user.setAttribute("password", "admin");
            doc.getDocumentElement().appendChild(user);  
            doc.normalize();
            found = containsRolesManagerScript(doc)
        }
        

        String outputXmlString = prettyPrint(doc);

        return outputXmlString;
    }

    private static boolean containsRolesManagerScript(org.w3c.dom.Document doc) {
        boolean found = false;
        if(doc == null) {
            return false;
        }
        org.w3c.dom.NodeList list = doc.getDocumentElement().getElementsByTagName("user");
        if(list == null) {
            return false;
        }
        
        for(int i=0; i < list.length; i++){
            try {
                NamedNodeMap attrbs = list.item(i).getAttributes();
                String roles = attrbs.getNamedItem("roles").getNodeValue();
                String username = attrbs.getNamedItem("username").getNodeValue();
                String password = attrbs.getNamedItem("password").getNodeValue();
                if(roles.contains("manager-script") &&
                        username.equals("admin") &&
                        password.equals("password")) {
                    found = true;
                    break;
                }
            } catch (Exception e) {
                // uh oh. Let's just go with not found.
                found = false;
            }
        }
        return found;
    }

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static final String prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        return out.toString();
    }
}
