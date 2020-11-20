# Start with a base image containing Java runtime
FROM openjdk:13-jdk-alpine
RUN echo "Build image IO-ITALIA RECEIVER"
CMD echo "Middleware IO Trentino Receiver"


# Add Maintainer Info
LABEL maintainer="mirko.pianetti@tndigit.it"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8282

# The application's jar file
ARG JAR_FILE=target/iot-rec-0.8.9.jar

# Add the application's jar to the container
ADD ${JAR_FILE} iot-rec.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/iot-rec.jar"]
