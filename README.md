## Product Data Management Service
This service supply entity data CURD management API.

## Dependencies
Java 8
MySQL
Redis
[ES Core Package](https://github.com/EasyAssessSystem/core)
[ES Authentication Service](https://github.com/EasyAssessSystem/authentication-service)


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
mvn clean install
java -jar target/easyassess-pdm-0.0.1.jar
```


