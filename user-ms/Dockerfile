FROM openjdk:14.0.1-slim

RUN mkdir /opt/application; apt update; apt install -y curl
COPY target/user-ms.jar /opt/application


ENTRYPOINT [ "sh", \
             "-c", \
             "exec java -Xmx768m -Xms256m -jar  -Dspring.profiles.active=local /opt/application/user-ms.jar"]
