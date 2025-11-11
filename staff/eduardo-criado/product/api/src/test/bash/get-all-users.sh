#!/bin/bash

# Test for GetAllUsersServlet with JWT authentication

# echo "=== Test GetAllUsersServlet with JWT ==="
# echo ""

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

# # Step 2: Get all users using the token
# echo "2. Getting all users with the token..."
# curl -X GET http://localhost:8080/api/users/all \
#   -H "Authorization: Bearer $TOKEN" \
#   -v


  
curl -X GET http://localhost:8080/api/users/all \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNzJlMzhjMi1jODQ4LTQ0MDUtYTBhNS02ZDQ0ZTVjY2Y2MzgiLCJpYXQiOjE3NjI4NjExMDcsImV4cCI6MTc2Mjk0NzUwN30.GLlDqRZfd66E1CPW-nbjj8VYPwQi2eyuFZcymcXd-h4" \
  -v
