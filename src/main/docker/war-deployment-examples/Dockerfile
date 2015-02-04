FROM rossbachp/apache-tomcat8:latest

ADD ${project.build.finalName}.war /webapps/${project.build.finalName}.war

CMD ["/opt/tomcat/bin/tomcat.sh"]