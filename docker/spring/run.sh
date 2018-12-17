#SCRIPT RODA NO JENKINS
#PRE-BUILD
#TST
CONTAINER_DOCKER=$(docker ps -a | grep -E '*mpog-gestaoriscos-tst' | awk -e '{print $1}')
IMAGEM_DOCKER=$(docker images | grep -E '*mpog-gestaoriscos.*latest-tst' | awk -e '{print $3}')
docker stop $CONTAINER_DOCKER
docker rm $CONTAINER_DOCKER
docker rmi -f $IMAGEM_DOCKER

#MVN BUILD
#TST
clean install -DskipTests -U

#POST-BUILD
#TST
docker run -d \
  -p 10015:8080 \
  --name mpog-gestaoriscos-tst \
  --net mpog-net \
  --ip 172.18.2.12 \
  -e "TZ=America/Sao_Paulo" \
  honiara.basis.com.br/jenkins/mpog-gestaoriscos:latest-tst
