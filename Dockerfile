FROM eclipse-temurin:17-jre-alpine

COPY target/*.jar /opt/webapp.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/webapp.jar"]