@echo off
echo ================================
echo 🚀 Demarrage du projet Spring Boot
echo ================================

:: Définir le chemin du dossier actuel
set "PROJECT_DIR=%~dp0"

:: Définir Maven local (fourni dans le projet)
set "MAVEN_HOME=%PROJECT_DIR%apache-maven-3.9.6"
set "PATH=%MAVEN_HOME%\bin;%PATH%"

:: Aller dans le dossier backend (relatif)
cd /d "%PROJECT_DIR%backend"

:: Lancer Spring Boot
call mvn clean spring-boot:run

pause

