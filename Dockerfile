FROM maven:3.6.3-openjdk-17

WORKDIR /app
ADD . /app

EXPOSE 4567
ENTRYPOINT ["mvn", "-f", "/app/pom.xml", "clean", "verify", "exec:java"]