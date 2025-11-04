curl -X POST http://localhost:8080/api/users/auth \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{"username":"juanperez","password":"pass123"}' \
  -v