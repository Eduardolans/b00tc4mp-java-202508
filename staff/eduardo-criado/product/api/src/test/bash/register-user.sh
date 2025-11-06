#!/bin/bash

# Test for RegisterUserServlet

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}' \
  -v

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ana López","username":"analopez","password":"pass456"}' \
  -v

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Carlos Ruiz","username":"carlosruiz","password":"pass789"}' \
  -v

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"María Gómez","username":"mariagomez","password":"pass321"}' \
  -v

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Luis Martínez","username":"luismartinez","password":"pass654"}' \
  -v

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Sofía Torres","username":"sofiatorres","password":"pass987"}' \
  -v
