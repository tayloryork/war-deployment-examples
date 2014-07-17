war-deployment-examples
=======================

This project is an example of how to deploy wars to your server with Chocolatey for Windows and RPM's for Unix

### How to create a packaged war for easy deployment
To create packages, run: `mvn install` This will run mvn compile, mvn test, mvn package, and mvn install (and maybe a few more in there).

`mvn package` will create an rpm and a chocolatey package.  These packages depend on Apache Tomcat 7.

`choco install war-deployment-example` will install the package on windows, and `rpm war-deploment-examples.rpm` will install the package on unix.

###The install process is (basically) as follows:

1. Determine Dependencies (Tomcat, etc)
2. Install Dependencies
3. Install Package files
4. Post Install Package - Call a deployment script to tell Tomcat "HEY IM A WAR, DEPLOY ME!"
5. Done

###The uninstall process is (basically) as follows:

1. Pre-Uninstall - Call a deployment script to tell Tomcat "HEY UNDEPLOY ME!"
2. Uninstall files
3. Done

