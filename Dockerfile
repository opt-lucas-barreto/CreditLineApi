FROM openjdk:17-jdk
ARG JAR_FILE=target/creditline-api-0.1.jar
WORKDIR /usr/app
COPY ${JAR_FILE} creditline-api.jar
RUN sh -c 'touch creditline-api.jar'
ENTRYPOINT [ "java","-jar","creditline-api.jar" ]
