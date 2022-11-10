FROM adoptopenjdk:11.0.11_9-jdk-hotspot-focal@sha256:8d3440bde3ef0c85bb20146b59e8534bd1aa1000d1f45819a76d3ba2c8b1c248
WORKDIR /riichi-app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]