#SCRIPT RODA NO JENKINS
#GERAÇÃO DO .TAR.GZ
cd codigo_fonte/cliente
npm install && bower install --allow-root --save
gulp
cd dist
tar czfv gestaoriscos-cliente.tar.gz ./*
cp gestaoriscos-cliente.tar.gz ../../../docker/nginx/
chmod -R a+x ../../../docker/nginx

#PRÉ-ENVIO
#TST
CONTAINER_DOCKER=$(docker ps -a | grep -E '*mpog-gestaoriscos-nginx-tst*' | awk -e '{print $1}')
docker stop $CONTAINER_DOCKER
docker rm $CONTAINER_DOCKER

#
#
#
# DOCKER BUILD AND PUBLISH 
#
#
#

#PÓS-ENVIO
#TST
docker run -d --name mpog-gestaoriscos-nginx-tst -p 50086:80 --net mpog-net --ip 172.18.2.12 \
-e ENDERECO_API="172.18.2.12:8080" \
honiara.basis.com.br/jenkins/mpog-nginx:gestaoriscos-tst
