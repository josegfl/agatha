FROM postgres:9.5.4
ENV POSTGRES_PASSWORD="Mo680A94dS2RvHmUDA8Y"
ADD mpoginit.sh /
RUN cat /mpoginit.sh >> /docker-entrypoint-initdb.d/postgis.sh

