package co.tyec.warDeploymentExamples

import static org.testng.Assert.*
import groovy.xml.XmlUtil

import org.testng.annotations.Test
import org.apache.commons.cli.ParseException;

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
        String inputFilePath = options.f.trim();
        File inputFile = new File(inputFilePath)
        def inputXmlString = inputFile.getText();
        
        // read xml
        Node inputXml = new XmlParser().parseText(inputXmlString)

        // add user
        def outputXml = addManagerUser(inputXml);

        // write xml
        inputFile.setText(outputXml);
        println "Writing to " + inputFilePath
    }

    static def addManagerUser(Node input) {
        // add <user username="tomcat" password="tomcat" roles="tomcat" /> to xml doc
        def managerUser = new Node(null,
                'user',
                [username:"admin", password:"admin", roles:"manager-gui,manager-script"]);
        input.children().add(managerUser);
        
        String outputXmlString = XmlUtil.serialize(input);
        return outputXmlString;
    }

}
