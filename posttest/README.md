To build docker image for Rest API
```commandline
docker build -t posttest_api:1.0 . 
```
or
```commandline
docker-compose -f .\docker-compose.yml build 
```
To run docker compose (Rest API & Postgres)
```commandline
docker-compose -f .\docker-compose.yml up 
```
