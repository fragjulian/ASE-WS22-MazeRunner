FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY BE_MazeRunner/pom.xml /workspace
COPY BE_MazeRunner/src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]