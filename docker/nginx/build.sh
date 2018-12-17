#!/bin/bash
cd ../../codigo-fonte/cliente
npm install
npm run build
cd dist
tar czfv dist.tar.gz ./*
rm ../../../docker/nginx/dist.tar.gz
mv dist.tar.gz ../../../docker/nginx/
chmod -R a+x ../../../docker/nginx
cd ../../../docker/nginx
sudo docker build -t mpog-nginx:gestaoriscos-tst .
sudo docker-compose up
