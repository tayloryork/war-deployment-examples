package co.tyec.warDeploymentExamples

import static org.testng.Assert.*
import groovy.xml.XmlUtil

import org.testng.annotations.Test
import org.apache.commons.cli.ParseException;

public class AddUserToTomcatTest {
    
    def TOMCAT_USERS_BLANK = '''<?xml version='1.0' encoding='cp1252'?>
            <tomcat-users>
            </tomcat-users>'''
    
    def TOMCAT_TEST_FILE = './target/tomcat-users.xml';

    @Test
    void testAddMangerUser() {
        def TOMCAT_USERS = '''<?xml version='1.0' encoding='cp1252'?>
            <tomcat-users>
            <!-- some comment -->
            </tomcat-users>'''
        
        AddUserToTomcat autt = new AddUserToTomcat();
        String outputXmlString = autt.addManagerUser(TOMCAT_USERS);
        
        assertTrue(outputXmlString.contains("<user"), "User record was not added: " + outputXmlString);
    }
    
    @Test
    void mainTest() {
        String fileLocation = "";
        try {
            File tempFile = new File(TOMCAT_TEST_FILE);
            tempFile.setText(TOMCAT_USERS_BLANK);
            fileLocation = tempFile.getAbsolutePath().toString();
        } finally {
        }
        File file2 = new File(fileLocation);
        
        AddUserToTomcat.main("-f " + fileLocation)
        
        String outputText = "";
        try {
            File tempFile = new File(TOMCAT_TEST_FILE)
            outputText = tempFile.getText();
        } finally {
        }
        assertTrue(outputText.contains("<user"))
    }
}
