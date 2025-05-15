#!/bin/bash
echo "Running tests..."
mvn test

if [ $? -ne 0 ]; then
  echo "Tests failed! Exiting container."
  exit 1
fi

echo "Tests passed. Starting product service..."
# Replace below with your actual start command, for example:
java -jar target/product-service.jar
