FROM nginx:1.10.0

COPY default.conf /etc/nginx/conf.d/
COPY proxy.conf /etc/nginx/
COPY env.sh /etc/nginx/
ADD  dist.tar.gz /usr/share/nginx/html/

RUN sed -i 's|${ENDERECO_API}|'"$ENDERECO_API"'|' /etc/nginx/conf.d/default.conf

CMD ["sh", "-c", "/etc/nginx/env.sh ; nginx -g 'daemon off;'"]
