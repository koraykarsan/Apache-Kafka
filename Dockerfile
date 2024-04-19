FROM maven:3.6.3-jdk-11-slim as build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:resolve
COPY src /app/src
RUN mvn package
FROM openjdk:11-jre-slim
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]