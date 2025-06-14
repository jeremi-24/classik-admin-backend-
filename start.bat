@echo off
echo ================================
echo Demarrage du projet Spring Boot
echo ================================

set MAVEN_HOME=%~dp0apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

cd /d C:\back\skool
call mvn clean spring-boot:run

pause
