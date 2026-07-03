#!/bin/bash

# Définition des variables
APP_NAME="testFramework"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/opt/tomcat/webapps"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# Nettoyage et création du répertoire temporaire
if [ -f "$BUILD_DIR/" ]; then
rm -rf $BUILD_DIR/*
fi

mkdir -p $BUILD_DIR/WEB-INF/classes

# Compilation des fichiers Java avec le JAR des Servlets
find $SRC_DIR -name "*.java" > sources.txt

# On ajoute essai.jar au classpath en utilisant le séparateur ":"
javac -cp "$SERVLET_API_JAR:$LIB_DIR/essai.jar" -d $BUILD_DIR/WEB-INF/classes @sources.txt
rm sources.txt

cp $LIB_DIR/* $BUILD_DIR/WEB-INF/lib/

# Copier les fichiers web (web.xml, JSP, etc.)
cp -r $WEB_DIR/* $BUILD_DIR/

# Générer le fichier .war dans le dossier build
cd $BUILD_DIR || exit
jar -cvf $APP_NAME.war *
cd ..

# Déploiement dans Tomcat
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo ""

echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."

echo ""
