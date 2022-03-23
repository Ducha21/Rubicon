FROM docker-registry.local:5000/openjdk:8-jdk
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx512m", "/app.war", "--spring.config.location=classpath:/application.yml,/u09_r/config/application.yml"]
