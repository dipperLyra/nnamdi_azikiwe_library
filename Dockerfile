FROM openjdk:8-jdk-alpine
MAINTAINER echerecreatn@gmail.com
VOLUME /tmp
ADD target/nnamdi_azikiwe-0.0.1-SNAPSHOT.jar nnamdi_azikiwe_lib.jar
ADD data /tmp/data
EXPOSE 8033
ENTRYPOINT ["java", "-jar", "nnamdi_azikiwe_lib.jar"]