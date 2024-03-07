

### Setup local config server with sample config from github
```sh
docker run --name config-server -dit -p 8888:8888 -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/spring-cloud-samples/config-repo hyness/spring-cloud-config-server
```

### Setup conductor locally
```sh 
docker run --name conductor -dit -p 1080:8080 -p 1234:5000 orkesio/orkes-conductor-community-standalone:latest
```
- [Conductor UI](http://localhost:1234)
- [Conductor API Swagger](http://localhost:1080/swagger-ui/index.html)

### Submit workflow
```sh 
curl -H 'Content-Type: application/json' http://localhost:8080/submit/ -d '{"someId": 123}'
```