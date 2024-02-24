# REST API implementation with Spring MVC framework

This project contains REST API implementation example with Spring MVC framework. You'll need basic knowledge of Java before you can start exploring this project.

## Steps to run server locally

Before you start the server, please start the MySQL server.

You need Tomcat server for project execution. You can either create a WAR file and deploy in the tomcat server, or you can execute it through the IDE after adding tomcat server.

I used Eclipse IDE for this specific project to run this project through Tomcat.

Access the API through http://localhost:8080/sample-springmvc-api/city/pune once the deployment is successful in Tomcat.

Please chech Dockerfile for more information on execution steps in Tomcat server.

## Unit tests

This project also includes the unit tests for the implementation.

To run all the unit test - `mvn test`

## Extras 

I've use this command to create the project - `mvn archetype:generate -DgroupId=io.github.rajendrasatpute -DartifactId=sample-springmvc-api -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4`. And then added Spring dependencies as per the requirements.