
FROM openjdk:11-jdk-slim
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring
ADD target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
