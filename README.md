# CI/CD Pipeline with Github Actions and Docker to deploy a Spring Boot REST API

This is a sample project for creating a CI/CD pipeline with Github Actions and Docker to deploy a Spring Boot REST API.

## Prerequisites

Before you begin, make sure you have the following:

- A Github account
- A Docker Hub account
- A Spring Boot REST API project
- Docker installed on your local machine


## Getting Started

### Step 1: Set up Github Repository

Create a new Github repository for your Spring Boot REST API project.

### Step 2: Create a Dockerfile

Create a Dockerfile in the root directory of your project. 

Before that you have to build the project it generates .jar file in the target folder 

```Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/my-api-0.0.1-SNAPSHOT.jar /app/my-api.jar

ENTRYPOINT ["java", "-jar", "my-api.jar"]
```

This Dockerfile uses the openjdk:17-jdk-slim image as the base image, sets the working directory to /app, copies the Spring Boot JAR file into the container, and runs the JAR file when the container starts.

### Step 3: Create Github Actions Workflow

Create a new file called .github/workflows/main.yml in the root directory of your project. This file will contain the instructions for building, testing, and deploying your application.


```main.yml
  name: build and deploy spring-api
  on:
    push:
      branches:
        - main

  jobs:
    build-deploy:
      name: build and deploy spring-api
      runs-on: ubuntu-20.04
      steps:
        - name: checkout code
          uses: actions/checkout@v3

        - name: setup jdk 17
          uses: actions/setup-java@v3
          with:
            distribution: 'corretto'
            java-version: 17

        - name: unit tests
          run: mvn -B test --file pom.xml

        - name: build the app
          run: |
            mvn clean
            mvn -B package --file pom.xml
        - name: build the docker image
          uses: docker/build-push-action@v4
          with:
            context: .
            dockerfile: Dockerfile
            push: false
            tags: ${{ secrets.DOCKER_HUB_USERNAME }}/rest-api:latest

        - name: login to docker hub
          uses: docker/login-action@v1
          with:
            username: ${{ secrets.DOCKER_HUB_USERNAME }}
            password: ${{ secrets.DOCKER_HUB_ACCESS_TOKEN }}

        - name: push the docker image to docker hub
          uses: docker/build-push-action@v4
          with:
            context: .
            dockerfile: Dockerfile
            push: true
            tags: ${{ secrets.DOCKER_HUB_USERNAME }}/rest-api:latest
```

This main.yml file sets up a workflow that runs on every push to the main branch. It has one job:

- build-deploy: Builds the Docker image, tags it with the latest, and pushes it to Docker Hub.


### Step 4: Push to Github Repository

Commit and push your changes to your Github repository. The Github Actions workflow should automatically trigger and build your Docker image.

### Step 5: Configure Github Secrets

To securely store your Docker Hub credentials, you'll need to create Github secrets. Go to your Github repository's settings and click on the "Secrets" tab. Create two secrets:

DOCKER_HUB_USERNAME: Your Docker Hub username
DOCKER_HUB_ACCESS_TOKEN: Your Docker Hub access token
