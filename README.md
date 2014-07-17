war-deployment-examples
=======================

This project is an example of how to deploy wars to your server with Chocolatey for Windows and RPM's for Unix

To create packages, run: ```mvn install``` This will run mvn compile, mvn test, mvn package, and mvn install (and maybe a few more in there).

```mvn package``` will create an rpm and a chocolatey package.  These packages depend on Apache Tomcat 7.

```choco install war-deployment-examples``` will install the package on windows, and ```rpm war-deploment-examples.rpm`` will install the package on unix.

The install process is as follows:
Determine Dependencies (Tomcat, etc)
Install Dependencies
Install Package files
Post Install Package - Call a deployment script to tell Tomcat "HEY IM A WAR, DEPLOY ME!"
Done

The uninstall process is as follows:
Pre-Uninstall - Call a deployment script to tell Tomcat "HEY UNDEPLOY ME!"
Uninstall files
Done

