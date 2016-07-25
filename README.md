## Product Data Management Service
This service supply entity data CURD management API.

## Dependencies
```
Java 8
MySQL
Redis
ES Core Package
ES Authentication Service
```


## Development Installation
### 1. Make sure get and build ES core package
```sh
git clone git@github.com:EasyAssessSystem/core.git
mvn clean install 
```
### 2. Set up ES Authentication Service
```sh
git clone git@github.com:EasyAssessSystem/authentication-service.git
node src/server.js dev
```
### 3. Install and start redis
```sh
brew install redis
redis-server
```
### 4. Initialize DB
```sh
git clone git@github.com:EasyAssessSystem/pdm.git
bash db-schema/upgrade.sh
```
### 5. Build and start service
```sh
export ES_ENV=dev
mvn clean install
java -jar target/easyassess-pdm-0.0.1.jar
```
NOTE: If you run service from IntelliJ, you need to set env value in your launch configuration

### 5. CI
http://103.227.51.161:1338/job/PDM/


