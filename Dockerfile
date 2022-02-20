FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/api.jar api.jar
ENTRYPOINT ["java","-jar","/api.jar"]