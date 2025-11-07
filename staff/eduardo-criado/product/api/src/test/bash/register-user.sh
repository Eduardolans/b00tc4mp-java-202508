#!/bin/bash

# Test for RegisterUserServlet

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}' \
  -v


