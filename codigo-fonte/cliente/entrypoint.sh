#!/bin/sh

set -eu

npm install &&\
npm run build &&\

cd dist
tar zcvf dist.tar.gz * 