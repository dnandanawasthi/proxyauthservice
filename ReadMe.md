###This is sample spring boot based service to authenticate user.
it will be the first proxy service to authenticate user and then other services can be accessed
based on the request.

 1. Spring Boot
 2. JWT (0.6.0)
 3. Spring Data
 4. H2 DB
 5. Java 1.8

this can be executed by as jar or docker container

###Prerequisite: 
Get Code : [Github Code Link](git clone https://github.com/dnandanawasthi/card-service.git)
```
cd card-service
mvn clean install
java -jar target/card-service-0.0.1-SNAPSHOT.jar
```

Open Postman
Use HTTP POST method and URL: ```http://localhost:8080/card-service/card/addcard```
```
{ "id" : "1", "userName" : "nandan", "cardNumber" : "12345", "expiryDate" : "28/12/1987", "email" : "nandan@gmail.com" }
```
User HTTP GET method and URL: http://localhost:8082/card-service/card/cardbynumber/12345

to execute as JAR
```
cd proxyauthservice
mvn clean install
java -jar target/auth-service-1.0-SNAPSHOT.jar
```
Open Postman
select http method POST
```
set URL http://localhost:8080/token/generate-token
set header Content-Type - application/json
set body
{
	"username": "nandan",
	"password": "password"
}
```
As a result you will get token like this
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYW5kYW4iLCJzY29wZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlzcyI6Imh0dHA6Ly9hdXRoc2VydmljZS5jb20iLCJpYXQiOjE1MjkzNTMyMzEsImV4cCI6MTUyOTM3MTIzMX0.mKAupn6-Rii6CXW3pTGHqQdaatu2T8dyX_L1Pdakr0s"
}
```

Open new tab on Postman
select http method Get
```
set URL <http://localhost:8080/services?url=card-service_card_cardbynumber_12345>
set header Content-Type - application/json
set header Authorization - <received token in preview request>
```