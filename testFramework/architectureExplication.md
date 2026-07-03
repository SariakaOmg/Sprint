# Explication de l'architecture et du flux de requête

Ce document décrit, étape par étape, comment une requête HTTP est traitée dans ton projet de test utilisant un Front Controller.

1) Déclaration du servlet
- Où : [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml#L6-L10)
- Description : `web.xml` lie un nom logique (`FrontController`) à la classe `main.java.FrontControllerServlet`. Le conteneur (Tomcat) cherchera cette classe dans `WEB-INF/classes` ou dans les JARs de `WEB-INF/lib` (par ex. `build/WEB-INF/lib/essai.jar`).

2) Mapping d'URL
- Où : [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml#L12-L16)
- Description : le `url-pattern` `/*` indique que TOUTES les requêtes sont routées vers le `FrontController`. C'est la raison pour laquelle même les accès à `/liste.jsp` passent d'abord par ce servlet.

3) Chargement de la classe au runtime
- Où regarder : `build/WEB-INF/lib/essai.jar` (ou `WEB-INF/classes` si tu copies les .class)
- Description : si `essai.jar` contient `main/java/FrontControllerServlet.class`, Tomcat le charge et instancie la servlet.

4) Initialisation (init)
- Quand : à la première requête qui cible la servlet (ou au démarrage si `<load-on-startup>` est défini)
- Ce qui se passe : Tomcat appelle `init()` sur l'instance. `doGet`/`doPost` ne sont pas appelés pendant l'initialisation.

5) Traitement d'une requête HTTP (flux exact)
- Étape A : Client envoie une requête (ex : `GET /liste`)
- Étape B : Tomcat compare l'URL au mapping (`/*`) et sélectionne `FrontController`.
- Étape C : Tomcat crée `HttpServletRequest` et `HttpServletResponse`, puis appelle `service()` sur la servlet.
- Étape D : `service()` appelle `doGet()` ou `doPost()` selon la méthode HTTP; dans ton framework `doGet`/`doPost` appellent `processRequest(...)`.
- Étape E : `processRequest(...)` exécute la logique (dans ton cas `response.getWriter().println("URL: " + url);`), et la sortie est envoyée au client.

=> Conclusion : tu vois `URL: ...` parce qu'une REQUÊTE a été traitée et que `processRequest` a écrit dans la réponse. Ce n'est pas dû à l'instanciation.

6) Interaction avec les JSP
- JSP : [src/main/webapp/liste.jsp](src/main/webapp/liste.jsp)
- Si le `FrontController` écrit directement dans la réponse, la JSP ne sera pas exécutée.
- Pour utiliser la JSP, le `FrontController` doit faire un `forward` :

```java
request.getRequestDispatcher("/liste.jsp").forward(request, response);
```

Après le `forward` Tomcat exécute la JSP et renvoie son rendu au client.

7) Options pratiques
- Changer le mapping (`url-pattern`) pour limiter l'interception (ex. `/app/*` au lieu de `/*`).
- Modifier le code du `FrontController` (dans ton framework) pour qu'il prépare des attributs `request.setAttribute(...)` puis `forward` vers la JSP.
- Vérifier le contenu de `essai.jar` si tu veux confirmer où se trouve `processRequest`.

8) Rappels rapides
- `web.xml` ne déclenche pas `doGet`/`doPost` automatiquement ; ce sont les requêtes HTTP qui les déclenchent.
- `load-on-startup` provoque l'appel à `init()` au démarrage, mais pas aux `doGet`/`doPost`.

Si tu veux, je peux ajouter un exemple de `FrontControllerServlet` qui fait le `forward` vers `/liste.jsp` et positionne l'attribut `cheques` attendu par la JSP.