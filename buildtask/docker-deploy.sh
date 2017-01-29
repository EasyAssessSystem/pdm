#!/usr/bin/env bash

TARGET_SERVER="59.110.152.96"
ENV_VARS="[\"ES_ENV=prod\",\"ES_AUTHENTICATION_SERVER=${PROD_AUTH_SERVER}\",\"ES_AUTHENTICATION_PORT=1337\",\"ES_SESSION_SERVER=${PROD_SESSION_SERVER}\",\"ES_SESSION_PORT=6379\",\"ES_DB_SERVER=${PROD_DB_SERVER}\",\"ES_DB_PORT=3306\",\"ES_DB_USER=${PROD_DB_USER}\",\"ES_DB_PASSWORD=${PROD_DB_PASSWORD}\"]"
DOCKER_IMAGE="registry-vpc.cn-beijing.aliyuncs.com/easyassess/pdm-service"
HOST_CONFIG="{\"PortBindings\":{\"8180/tcp\": [{ \"HostPort\": \"8180\"}]}}"
CONTAINER_CONFIG="{\"Name\": \"pdm-service\",\"Image\": \"$DOCKER_IMAGE\", \"Env\":$ENV_VARS, \"ExposedPorts\": {\"8180/tcp\": {}}, \"HostConfig\": $HOST_CONFIG}"
#pull image
curl -v -X POST "http://$TARGET_SERVER:2376/images/create?fromImage=$DOCKER_IMAGE"
curl -v -H "Content-type: application/json" -X DELETE "http://$TARGET_SERVER:2376/containers/pdm-service?force=true"
curl -v -H "Content-type: application/json" -X POST -d "$CONTAINER_CONFIG" "http://$TARGET_SERVER:2376/containers/create?name=pdm-service"
curl -v -H "Content-type: application/json" -X POST "http://$TARGET_SERVER:2376/containers/pdm-service/start"