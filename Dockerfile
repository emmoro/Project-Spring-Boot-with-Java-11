FROM openjdk:11-jre
ENTRYPOINT ["/usr/local/openjdk-11/bin/java", "-jar", "spring-boot-rest-docker.jar"]
ADD target/spring-boot-rest-docker-1.0.0.jar spring-boot-rest-docker.jar