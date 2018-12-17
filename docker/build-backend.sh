#!/bin/bash
cd ../codigo-fonte/servico
mvn clean install -DskipTests
rm ../../docker/spring/app.jar
mv target/app.jar ../../docker/spring
cd ../../docker/spring
#sudo docker build -t mpog-gestaoriscos:latest-tst .
#sudo docker-compose up
