#!/bin/sh

set -eu

cd ../codigo-fonte/cliente
npm install -g bower
bower install --allow-root
npm install
npm run build

cd dist
tar zcvf dist.tar.gz *
mv dist.tar.gz ../../../docker/nginx

cd ../../../docker/nginx
chmod -R a+x *.sh
