# Force l'utilisation de Java 21 ou supérieur pour Tomcat 10
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
# (Ou le chemin vers ton JDK plus récent si tu l'as installé manuellement dans /opt/)
# Supprimer dans le bin
rm -rf bin/*
if [ -f "essai.jar" ]; then
rm essai.jar
fi
# 1. Crée un dossier temporaire pour stocker les fichiers compilés (.class)
mkdir -p bin

# 2. Compile ton code
javac -cp "lib/servlet-api.jar" -d bin src/utils/ClasseMethodeMap.java src/mg/itu/URLMapping.java src/utils/Scannerrrs.java src/mg/itu/Controller.java src/main/java/FrontControllerServlet.java 

# 3. Crée le fichier JAR
jar -cvf essai.jar -C bin .

cp -f essai.jar ../testFramework/lib/
# --- AVANT de lancer Tomcat, n'oublie pas de copier ton dossier projet-test dans webapps ! ---
# cp -r /chemin/vers/projet-test /home/asus/Documents/apache-tomcat-10.1.28/webapps/

# Va dans le dossier bin de ton Tomcat
cd /opt/tomcat/bin

# Relance proprement
./shutdown.sh
./startup.sh