#!/bin/bash

echo "start sonar server"
bash $HOME/sonarqube-5.3/bin/macosx-universal-64/sonar.sh start
sleep 10
mvn sonar:sonar

mvn test -Dtest=CheckSonarReportTest
bash $HOME/sonarqube-5.3/bin/macosx-universal-64/sonar.sh stop
mvn test
