#!/usr/bin/env bash

ES_ENV=${ES_ENV}
ES_AUTHENTICATION_SERVER=${ES_AUTHENTICATION_SERVER}
ES_AUTHENTICATION_PORT=${ES_AUTHENTICATION_PORT}
ES_DB_SERVER=${ES_DB_SERVER}
ES_DB_PORT=${ES_DB_PORT}
ES_SESSION_SERVER=${ES_SESSION_SERVER}
ES_SESSION_PORT=${ES_SESSION_PORT}

CMD=$1

HOST_ADDRESS=$(ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')

if  [ ! -n "$ES_AUTHENTICATION_SERVER" ] ;then
    ES_AUTHENTICATION_SERVER=$HOST_ADDRESS
fi

if  [ ! -n "$ES_AUTHENTICATION_PORT" ] ;then
    ES_AUTHENTICATION_PORT=1337
fi

if  [ ! -n "$ES_DB_SERVER" ] ;then
    ES_DB_SERVER=$HOST_ADDRESS
fi

if  [ ! -n "$ES_DB_PORT" ] ;then
    ES_DB_PORT=3306
fi

if  [ ! -n "$ES_SESSION_SERVER" ] ;then
    ES_SESSION_SERVER=$HOST_ADDRESS
fi

if  [ ! -n "$ES_SESSION_PORT" ] ;then
    ES_SESSION_PORT=6379
fi

if [ ! -n "$CMD" ] ;then
    CMD=./entrypoint.sh
fi

docker rm -f pdm-service || echo "No started pdm service found"

docker run -it -p 8180:8180 --rm --name=pdm-service \
         -e ES_ENV=$ES_ENV \
         -e ES_AUTHENTICATION_SERVER=$ES_AUTHENTICATION_SERVER \
         -e ES_AUTHENTICATION_PORT=$ES_AUTHENTICATION_PORT \
         -e ES_DB_SERVER=$ES_DB_SERVER \
         -e ES_DB_PORT=$ES_DB_PORT \
         -e ES_SESSION_SERVER=$ES_SESSION_SERVER \
         -e ES_SESSION_PORT=$ES_SESSION_PORT \
         registry.cn-beijing.aliyuncs.com/easyassess/pdm-service \
         $CMD