FROM openjdk:22
EXPOSE 8080
WORKDIR /web
COPY agencies.json agencies.json
COPY countries.json countries.json
COPY tours.json tours.json
ADD target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]