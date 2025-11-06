#!/bin/bash

# Test for AuthenticateUserServlet

curl -X POST http://localhost:8080/api/users/auth \
  -H "Content-Type: application/json" \
  -d '{"username":"juanperez","password":"pass123"}' \
  -v