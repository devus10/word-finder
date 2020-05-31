FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE

RUN apk add --no-cache bash

COPY ${JAR_FILE} app.jar
COPY ./entrypoint.sh entrypoint.sh

RUN ["chmod", "+x", "entrypoint.sh"]

ENTRYPOINT ["./entrypoint.sh"]
