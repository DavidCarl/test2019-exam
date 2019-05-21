FROM maven:3.3-jdk-8 AS build-env

COPY backend /app
WORKDIR /app
RUN mvn clean package

FROM tomcat:9.0.1-alpine

COPY --from=build-env /app/target/2.war /usr/local/tomcat/webapps/2.war
EXPOSE 8080