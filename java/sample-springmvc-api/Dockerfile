# First stage: build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /springmvc-app
COPY . .
# RUN mvn verify -Dspring.profiles.active=dockerembbed
RUN mvn clean package -DskipTests

FROM tomcat:jre17-temurin-jammy as execute
COPY --from=build /springmvc-app/target/sample-springmvc-api-*.war /usr/local/tomcat/webapps/sample-springmvc-api.war
CMD ["catalina.sh", "run"]