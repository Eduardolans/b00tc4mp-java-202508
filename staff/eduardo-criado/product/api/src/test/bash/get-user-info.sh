#!/bin/bash

# Test for GetUserInfoServlet with JWT authentication

echo "=== Test GetUserInfoServlet with JWT ==="
echo ""

# # Step 1: Authenticate to get the token
# echo "1. Authenticating user to get token..."
# AUTH_RESPONSE=$(curl -s -X POST http://localhost:8080/api/users/auth \
#   -H "Content-Type: application/json" \
#   -d '{"username":"juanperez","password":"pass123"}')

# echo "Response: $AUTH_RESPONSE"
# echo ""

# # Extract the token from JSON response
# TOKEN=$(echo $AUTH_RESPONSE | grep -o '"token":"Bearer [^"]*"' | sed 's/"token":"Bearer //' | sed 's/"//')

# if [ -z "$TOKEN" ]; then
#     echo "Error: Could not get token. Make sure the user exists."
#     echo "You can create the user with: ./src/test/bash/register-user.sh"
#     exit 1
# fi

# echo "Token obtained: Bearer $TOKEN"
# echo ""

# # Step 2: Get authenticated user info
# echo "2. Getting authenticated user info from token..."
# curl -X GET http://localhost:8080/api/users/info \
#   -H "Authorization: Bearer $TOKEN" \
#   -v


  curl -X GET http://localhost:8080/api/users/info \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2NzEyN2RjZi0xODAxLTRlN2YtOTRkNi02YzUzNDNkZWUyMDgiLCJpYXQiOjE3NjM0NzM2NjIsImV4cCI6MTc2MzU2MDA2Mn0.FGwe1TkuB_VaFrooXEkKAuwa4rGHXf83nHq2yR56pcQ" \
  -v
