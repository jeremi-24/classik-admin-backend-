@echo off
setlocal EnableDelayedExpansion

echo.
echo ================================================
echo Vérification de la présence du JDK (javac)
echo ================================================

set "JAVAC_FOUND="
for /f "delims=" %%i in ('where javac 2^>nul') do (
    set "JAVAC_FOUND=%%i"
)

if not defined JAVAC_FOUND (
    echo [ERREUR] Le compilateur Java (javac) est introuvable.
    echo [INFO] Installe un JDK (Java Development Kit) 17 par exemple :
    echo        https://adoptium.net/fr/temurin/releases/?version=17
    echo.
    echo Appuie sur une touche pour quitter...
    pause >nul
    exit /b
) else (
    echo [OK] Le compilateur Java est disponible : !JAVAC_FOUND!
)

echo.
echo Fin de vérification – appuie sur une touche pour quitter...
pause >nul
endlocal
