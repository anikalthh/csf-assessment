FROM node:21 AS ng-builder

RUN npm i -g @angular/cli

WORKDIR /ngapp

COPY ecommerce/client/package*.json .
COPY ecommerce/client/angular.json .
COPY ecommerce/client/tsconfig.* .
COPY ecommerce/client/src src

RUN npm ci && ng build

# /ngapp/dist/frontend/browser/*

# Starting with this Linux server
FROM maven:3-eclipse-temurin-21 AS sb-builder

## Build the application
# Create a directory call /sbapp
# go into the directory cd /app
WORKDIR /sbapp

# everything after this is in /sbapp
COPY ecommerce/mvnw .
COPY ecommerce/mvnw.cmd .
COPY ecommerce/pom.xml .
COPY ecommerce/.mvn .mvn
COPY ecommerce/src src
COPY --from=ng-builder /ngapp/dist/client-side/browser/ src/main/resources/static

# Build the application
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app 

COPY --from=sb-builder /sbapp/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

## Run the application
# Define environment variable 
ENV PORT=8080 

# Expose the port
EXPOSE ${PORT}

# Run the program
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar