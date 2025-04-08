@echo off
:: Grand affichage ASCII RandyServlet
:: Décoration ASCII pour RandyServlet
type logo.txt
:: Configuration des variables
set BUILD_DIR=build
set WEBAPP_DIR=webapp
set SERVLET_API_JAR="D:\apache-tomcat-10.1.28\lib\servlet-api.jar"
set TOMCAT_WEBAPPS_DIR="D:\apache-tomcat-10.1.28\webapps"
set CATALINA_START="D:\apache-tomcat-10.1.28\bin"
set WAR_NAME=ETU003227.war
set PROJECT_DIR=servlet
set MODEL_DIR=models
set DAO_DIR=dao
set DRIVER_DIR="C:\mysql-connector-j-9.0.0.jar"

echo ======================================
echo Déploiement de projet Servlet
echo ======================================

:: Vérification des chemins
if not exist %SERVLET_API_JAR% (
    echo Erreur : servlet-api.jar introuvable.
    exit /b 1
)
if not exist %TOMCAT_WEBAPPS_DIR% (
    echo Erreur : Le dossier webapps de Tomcat est introuvable.
    exit /b 1
)

:: Étape 0 - Nettoyage du dossier build
if exist %BUILD_DIR% (
    echo Le dossier %BUILD_DIR% existe déjà. Suppression...
    rmdir /s /q %BUILD_DIR%
)

:: Étape 1 - Création des dossiers nécessaires
mkdir %BUILD_DIR%\WEB-INF\classes
if not exist lib mkdir lib 

:: Copie du fichier servlet-api.jar
copy %SERVLET_API_JAR% lib >nul
if errorlevel 1 (
    echo Erreur : Impossible de copier servlet-api.jar.
    exit /b 1
)

::Copie du driver
copy %DRIVER_DIR% lib >nul
if not exist src\main\webapp\WEB-INF\lib mkdir src\main\webapp\WEB-INF\lib
copy %DRIVER_DIR% src\main\webapp\WEB-INF\lib
:: Étape 2 - Compilation des fichiers Java
if not exist src\main\java\%PROJECT_DIR% (
    echo Erreur : Les fichiers source Java sont introuvables.
    exit /b 1
)
javac -cp "lib\servlet-api.jar;lib\mysql-connector-j-9.0.0.jar" -d %BUILD_DIR%\WEB-INF\classes src\main\java\servlet\*.java src\main\java\models\*.java src\main\java\dao\*.java
pause
if errorlevel 1 (
    echo Erreur lors de la compilation. Vérifiez vos fichiers Java.
    exit /b 1
)
:: Étape 3 - Copie des fichiers webapp
if not exist src\main\%WEBAPP_DIR% (
    echo Le dossier %WEBAPP_DIR% n'existe pas. Création...
    mkdir src\main\%WEBAPP_DIR%
    mkdir src\main\%WEBAPP_DIR%\WEB-INF
    echo Placez les fichiers nécessaires dans %WEBAPP_DIR%.
    exit /b 1
)
if exist src\main\%WEBAPP_DIR% (
    xcopy src\main\%WEBAPP_DIR% %BUILD_DIR% /e /i >nul
    if errorlevel 1 (
        echo Erreur : Impossible de copier les fichiers webapp.
        exit /b 1
    )
) else (
    echo Erreur : Le dossier %WEBAPP_DIR% est introuvable.
    exit /b 1
)
:: Étape 4 - Création du fichier WAR
where jar >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo Erreur : La commande jar n'est pas disponible. Ajoutez le JDK au PATH.
    exit /b 1
)
cd %BUILD_DIR%
jar -cvf %WAR_NAME% * >nul
if errorlevel 1 (
    echo Erreur : Échec de la création du fichier .war.
    exit /b 1
)
cd ..

:: Étape 5 - Déploiement vers Tomcat
copy %BUILD_DIR%\%WAR_NAME% %TOMCAT_WEBAPPS_DIR% >nul
if errorlevel 1 (
    echo Erreur : Impossible de copier le fichier .war.
    exit /b 1
)

:: Message de fin
echo =====================================================================
echo              Déploiement terminé avec succès !
echo Le fichier .war a été copié dans %TOMCAT_WEBAPPS_DIR%.
echo =====================================================================

:: Démarrer Tomcat 10
echo Démarrage de Tomcat 10...
set CATALINA_HOME=D:\apache-tomcat-10.1.28
call %CATALINA_START%\startup.bat


:: Pause pour laisser Tomcat s'initialiser
timeout /t 5 >nul

:: Ouvrir le navigateur
explorer http://localhost:8081

pause
