This is sample spring boot based service to authenticate user.
it will be the first proxy service to authenticate user and then other services can be accessed
based on the request.

 1. Spring Boot
 2. JWT (0.6.0)
 3. Spring Data
 4. H2 DB
 5. Java 1.8

this can be executed by as jar or docker container

to execute as JAR

cd proxyauthservice
mvn clean install
java -jar target/auth-service-1.0-SNAPSHOT.jar

Open Postman
select http method POST
set URL http://localhost:8080/token/generate-token
set header Content-Type - application/json
set body
{
	"username": "nandan",
	"password": "password"
}

As a result you will get token

Open new tab on Postman
select http method Get
set URL <http://host:port>
set header Content-Type - application/json
set header Authorization - <received token in preview request>





to execute as docker container

Command to build application: mvn clean install

Command to build docker image of application: mvn clean package docker:build

Command to build run docker image: docker run -it -p 8080:8080 auth-service

Command to stop all the container docker stop $(docker ps -a -q)

Command to remove all the container docker rm $(docker ps -a -q)

Command to remove all the image docker rmi -f $(docker images -q)