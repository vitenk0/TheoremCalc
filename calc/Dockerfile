FROM maven:3.6.3-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /target/calc-0.0.1-SNAPSHOT.jar calc.jar
EXPOSE 8080
CMD ["java", "-jar", "calc.jar"]