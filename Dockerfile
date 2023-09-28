FROM openjdk:19-slim
LABEL authors="Alexander Hofmann"

WORKDIR /project
COPY pom.xml /project
RUN apt update
RUN apt --yes upgrade
RUN apt --yes install maven
RUN mvn dependency:resolve
RUN mkdir -p /project/src
COPY src /project/src