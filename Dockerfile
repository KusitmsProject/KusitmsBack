FROM openjdk:11-jre-slim
EXPOSE 8081
ARG JAR_FILE=/home/ubuntu/docker/jenkinsDir/workspace/KusitmsProject/build/libs/KustimsBack-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]