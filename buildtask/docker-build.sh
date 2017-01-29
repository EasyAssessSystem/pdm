#!/usr/bin/env bash

export ES_ENV="prod"
mvn clean package -Dmaven.test.skip=true

docker build --no-cache=true -f ./buildtask/Dockerfile -t registry.cn-beijing.aliyuncs.com/easyassess/pdm-service:latest ./
