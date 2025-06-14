@echo off
echo ===============================================
echo 📦 Téléchargement et installation de Apache Maven
echo ===============================================

:: Dossier d'installation
set "INSTALL_DIR=C:\skool"

:: URL et nom du fichier zip
set "ZIP_URL=https://downloads.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
set "ZIP_FILE=%USERPROFILE%\Downloads\apache-maven.zip"

echo 🔽 Téléchargement de Maven 3.9.6...
powershell -Command "Invoke-WebRequest -Uri '%ZIP_URL%' -OutFile '%ZIP_FILE%'"

echo 📦 Extraction dans %INSTALL_DIR% ...
powershell -Command "Expand-Archive -Path '%ZIP_FILE%' -DestinationPath '%INSTALL_DIR%' -Force"

:: Définir les variables d'environnement utilisateur (pas besoin d'être admin)
setx MAVEN_HOME "%INSTALL_DIR%\apache-maven-3.9.6"
setx PATH "%PATH%;%INSTALL_DIR%\apache-maven-3.9.6\bin"




echo ✅ Installation terminée !
echo 🔁 Redémarre ton terminal pour appliquer les changements.
pause
