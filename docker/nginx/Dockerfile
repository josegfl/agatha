FROM nginx:1.10.0

COPY default.conf /etc/nginx/conf.d/
COPY proxy.conf /etc/nginx/
COPY env.sh /etc/nginx/
ADD  dist.tar.gz /usr/share/nginx/html/

RUN /etc/nginx/env.sh
RUN nginx -g 'daemon off;'
