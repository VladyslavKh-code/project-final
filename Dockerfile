FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/jira-1.0.jar /app/app.jar

COPY ./resources /app/resources

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar", "--spring.config.additional-location=file:/app/resources/"]
