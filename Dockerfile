
FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/*.jar dynamodb-sb.jar
EXPOSE 8080
#ENTRYPOINT exec java $JAVA_OPTS -jar dynamodb-sb.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar dynamodb-sb.jar
