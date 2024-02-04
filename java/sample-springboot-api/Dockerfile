# First stage: build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /springboot-app
COPY . .
# RUN mvn verify -Dspring.profiles.active=dockerembbed
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy as execute
EXPOSE 8080
COPY --from=build /springboot-app/target/sample-springboot-api-*.jar /sample-springboot-api.jar
CMD ["java", "-jar", "/sample-springboot-api.jar"]