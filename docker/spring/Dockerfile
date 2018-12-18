FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS="-server -Xmx2G -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"

ADD *.jar app.jar
RUN mkdir /config 
COPY /config/application.properties application.properties 
RUN sh -c 'touch /app.jar'

VOLUME ["/config"]
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/config/application.properties-jar /app.jar"]
