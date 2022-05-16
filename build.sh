#!/bin/sh
./mvnw clean package && docker build -t mangila/spring-reactive-r2dbc . && docker push mangila/spring-reactive-r2dbc