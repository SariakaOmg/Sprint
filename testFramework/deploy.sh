# #!/bin/bash

# # Définition des variables
# APP_NAME="testFramework"
# SRC_DIR="src/main/java"
# WEB_DIR="src/main/webapp"
# BUILD_DIR="build"
# LIB_DIR="lib"
# TOMCAT_WEBAPPS="/opt/tomcat/webapps"
# SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# # Nettoyage et création du répertoire temporaire
# if [ -f "$BUILD_DIR/" ]; then
# rm -rf $BUILD_DIR/*
# fi

# mkdir -p $BUILD_DIR/WEB-INF/classes

# # Compilation des fichiers Java avec le JAR des Servlets
# find $SRC_DIR -name "*.java" > sources.txt

# # On ajoute essai.jar au classpath en utilisant le séparateur ":"
# # javac -cp "$LIB_DIR/*" -d $BUILD_DIR/WEB-INF/classes @sources.txt
# javac -parameters -cp "$LIB_DIR/*" -d $BUILD_DIR/WEB-INF/classes @sources.txt
# # javac -cp "$SERVLET_API_JAR:$LIB_DIR/essai.jar" -d $BUILD_DIR/WEB-INF/classes @sources.txt
# rm sources.txt

# cp $LIB_DIR/* $BUILD_DIR/WEB-INF/lib/

# # Copier les JSP du dossier source vue vers WEB-INF du build
# mkdir -p $BUILD_DIR/WEB-INF
# if [ -d "$SRC_DIR/exemple/vue" ]; then
#     cp -f $SRC_DIR/exemple/vue/*.jsp $BUILD_DIR/WEB-INF/
# fi

# # Copier les fichiers web (web.xml, JSP, etc.)
# cp -r $WEB_DIR/* $BUILD_DIR/

# # Générer le fichier .war dans le dossier build
# cd $BUILD_DIR || exit
# jar -cvf $APP_NAME.war *
# cd ..

# # Déploiement dans Tomcat
# cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

# echo ""

# echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."

# echo ""

#!/bin/bash
cd "$(dirname "$0")" || exit 1   # toujours travailler depuis testFramework/

APP_NAME="testFramework"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/opt/tomcat/webapps"

# Vrai nettoyage
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR/WEB-INF/classes" "$BUILD_DIR/WEB-INF/lib"

find "$SRC_DIR" -name "*.java" > sources.txt
javac -parameters -cp "$LIB_DIR/*" -d "$BUILD_DIR/WEB-INF/classes" @sources.txt || exit 1
rm sources.txt

cp $LIB_DIR/* "$BUILD_DIR/WEB-INF/lib/"

# Fichiers web (web.xml, liste.jsp, ...)
cp -r $WEB_DIR/* "$BUILD_DIR/"

# JSP du dossier vue -> WEB-INF
cp -f "$SRC_DIR"/exemple/vue/*.jsp "$BUILD_DIR/WEB-INF/" || { echo "form.jsp non copié"; exit 1; }

# WAR
(cd "$BUILD_DIR" && jar -cvf "$APP_NAME.war" *)

# Supprimer l'ancien déploiement avant de copier
rm -rf "$TOMCAT_WEBAPPS/$APP_NAME" "$TOMCAT_WEBAPPS/$APP_NAME.war"
cp -f "$BUILD_DIR/$APP_NAME.war" "$TOMCAT_WEBAPPS/"

echo "Déploiement terminé."
