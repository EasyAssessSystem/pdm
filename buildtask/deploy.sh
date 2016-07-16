#!/usr/bin/env bash

service_name=easyassess-pdm
host_address=192.168.0.21
host_port=8180
uid=root
jar_home=/root/.jenkins/workspace/PDM/
api_service_path=/usr/esapp/api-services

function package()
{
    mvn -Dmaven.test.failure.ignore clean install
}

function startup()
{
    response=$(curl --write-out %{http_code} --silent --output /dev/null http://$host_address:$host_port/default/data/user/0)
    echo $response
    echo "shutting down http://$host_address:$host_port"
    curl -X POST http://$host_address:$host_port/shutdown
    ssh $uid@$host_address nohup java -jar $api_service_path/easyassess-pdm-0.0.1.jar &
}

function deploy()
{
    scp $jar_home/target/easyassess-pdm-0.0.1.jar $uid@$host_address:$api_service_path
}

function build()
{
    package
    deploy
    startup
}

build