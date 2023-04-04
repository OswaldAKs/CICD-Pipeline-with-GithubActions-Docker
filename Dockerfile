FROM openjdk:17-jdk-slim
ADD target/api-springboot-0.0.1-SNAPSHOT.jar springapi-docker.jar
ENTRYPOINT ["java", "-jar", "springapi-docker.jar"]
