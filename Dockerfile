FROM maven:3.8.3-openjdk-8 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/pedido-api.jar /app/pedido-api.jar

CMD ["java", "-jar", "/app/pedido-api.jar"]
