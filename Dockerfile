FROM adoptopenjdk/openjdk11-openj9:alpine-jre

MAINTAINER yisasthemanuel@gmail.com

#Variables de entorno
ENV EUREKA_URI http://localhost:8761/eureka
ENV CONFIG_SERVER http://localhost:8888

ARG JAR_FILE

ADD ${JAR_FILE} /app.jar 

EXPOSE 8760

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]