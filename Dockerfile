####################BUILD STAGE##################################
FROM maven:3.8.6-eclipse-temurin-11@sha256:275471286ef76cbd748c9a09d8bd732cd5bb60e01a56c27c5ba9caae39d59df9 AS build
WORKDIR /project
COPY . /project/
RUN mvn clean package spring-boot:repackage -DskipTests
RUN apt-get update && apt-get install -y binutils
RUN $JAVA_HOME/bin/jlink \
        --verbose \
        --add-modules java.base,java.xml,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
        --strip-debug \
        --no-man-pages \
        --no-header-files \
        --compress=2 \
        --output /customjre

############################################################

#################FINAL IMAGE#############################
FROM debian:buster-slim@sha256:e5fadb8c8db83d5ca1df8df5c5bce2acf5531fb96421c445909c338301793caa
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"
WORKDIR /app
COPY --from=build /project/target/riichi-calculator.jar /app/
COPY --from=build /customjre $JAVA_HOME
CMD ["java", "-jar", "riichi-calculator.jar"]
#############################################################