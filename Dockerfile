FROM openjdk:11.0.10-jdk-slim-buster
WORKDIR /opt
ADD target/MeetingPlanner-*.jar meeting-planner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/meeting-planner.jar"]