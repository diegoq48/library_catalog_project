FROM openjdk:8-jdk-alpine

WORKDIR /opt
RUN mkdir -p /opt/app 
COPY ./ /opt/app
WORKDIR /opt/app
RUN javac -d bin src/main/*.java src/interfaces/*.java src/data_structures/*.java
ENTRYPOINT ["java", "-cp", "bin", "main.main"] 