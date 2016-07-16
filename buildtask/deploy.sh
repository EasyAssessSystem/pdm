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
    if (( $(ps -ef | grep -v grep | grep $service_name | wc -l) > 0 ))
    then
        echo "$service_name is shutting down!!!"
        curl -X POST http://$host_address:$host_port/shutdown
    else
        echo "$service_name is not running. skip shutdown process!!!"
    fi

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