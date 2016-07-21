#!/usr/bin/env bash

service_name=easyassess-pdm
host_address=192.168.0.21
host_port=8180
uid=root
jar_home=/root/.jenkins/workspace/PDM/
api_service_path=/usr/esapp/api-services

function package()
{
    echo "building package"
    mvn -Dmaven.test.failure.ignore clean install
}

function startup()
{
    export PDM_ENV="prod"
    ssh $uid@$host_address service pdm start
}

function deploy()
{
    echo "shutting down http://$host_address:$host_port"
    ssh $uid@$host_address service pdm stop
    echo "deploying package to server"
    scp $jar_home/target/easyassess-pdm-0.0.1.jar $uid@$host_address:$api_service_path
}

function build()
{
    package
    deploy
    startup
}

build