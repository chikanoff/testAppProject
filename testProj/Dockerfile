FROM adoptopenjdk/openjdk8:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=build/libs/testProj-1.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
