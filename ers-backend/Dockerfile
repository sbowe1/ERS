FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/mydocker.jar mydocker.jar
ENTRYPOINT ["java", "jar", "/mydocker.jar"]