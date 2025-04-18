FROM openjdk:24-slim-bookworm

LABEL maintainer="Jospin Aurèle Donfack"

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

EXPOSE 2020

ENTRYPOINT ["java"]

CMD ["-jar", "/app/demo.jar"]