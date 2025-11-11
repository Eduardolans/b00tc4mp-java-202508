#!/bin/bash

# Reset all data in the API (for testing purposes)

echo "=== Resetting API data ==="
curl -X POST http://localhost:8080/api/test/reset \
  -H "Content-Type: application/json" \
  -v

echo -e "\n"
echo "✅ Data reset complete"
