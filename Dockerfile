FROM openjdk:17-alpine AS base
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:InitialRAMPercentage=25.0 -XX:MaxRAMPercentage=90.0 -XX:+UseZGC"
ENV TMP_DIR="/usr/src/app/data/temp"

WORKDIR /usr/src/app
COPY target/jokes-application-*.jar jokes-application.jar
ENTRYPOINT java ${JAVA_OPTS} -Djava.io.tmpdir=${TMP_DIR} -jar /usr/src/app/jokes-application.jar