docker build --network=host --tag sonar-scanner-image:latest --build-arg SONAR_HOST="http://localhost:9000" --build-arg SONAR_LOGIN_TOKEN="$SONARQUBE_TOKEN_RIVALMODEL" .
