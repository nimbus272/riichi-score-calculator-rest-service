FROM maven:3.8.3-adoptopenjdk-11@sha256:6711c1b68c24af2bb1fb250415e54a443cf9e68228241ff34cdff21599e1b8d4 AS build
WORKDIR /project
COPY . /project/
RUN mvn clean package spring-boot:repackage -DskipTests


FROM adoptopenjdk:11.0.11_9-jdk-hotspot-focal@sha256:8d3440bde3ef0c85bb20146b59e8534bd1aa1000d1f45819a76d3ba2c8b1c248
WORKDIR /riichi-app
COPY --from=build /project/target/riichi-calculator.jar /riichi-app/
CMD ["java", "-jar", "riichi-calculator.jar"]