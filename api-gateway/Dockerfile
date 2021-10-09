FROM openjdk:14.0.1-slim

RUN mkdir /opt/application; apt update; apt install -y curl
COPY target/api-gateway.jar /opt/application

EXPOSE 8080

ENTRYPOINT [ "sh", \
             "-c", \
             "exec java -Xmx768m -Xms256m -jar  -Dspring.profiles.active=local /opt/application/api-gateway.jar"]
