FROM openjdk:17-alpine
ADD target/api.jar api.jar
ENTRYPOINT ["java","-jar","/api.jar"]