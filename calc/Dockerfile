FROM maven:3.8.7-eclipse-temurin-19
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:19
COPY --from=build /calc/target/calc-0.0.1-SNAPSHOT.jar calc.jar
EXPOSE 8080
CMD ["java", "-jar", "calc.jar"]