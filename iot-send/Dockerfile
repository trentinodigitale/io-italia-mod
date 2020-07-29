# Start with a base image containing Java runtime
FROM openjdk:13-jdk-alpine
RUN echo "Build image IO-ITALIA SENDER"
CMD echo "Middleware IO Trentino Sender"


# Add Maintainer Info
LABEL maintainer="mirko.pianetti@tndigit.it"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8281

# The application's jar file
ARG JAR_FILE=target/iot-send-0.8.5.jar

# Add the application's jar to the container
ADD ${JAR_FILE} iot-send.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","iot-send.jar"]
