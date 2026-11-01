# Sprint 10
methode d action dans controller dev : associe url
=> misy amireny oe tsy maintsy connecter vao afaka miexecute an azy

# Sprint 10 bis
=> tsy maintsy manana role specifique vao azo miexecuter an azy

Todo :
    - mamorona annotation : [OK]
    - Web.xml config AuthSessionKey : [OK]
    - Comment on fait pour la session pendant la connection : - j ai prense mettre de l annotation @connect pour une fonction qui sert de connection , au moment de la connection il y aura une creation de session : 
    - Verification si le session AUtheSessionKey continet qulque chose = si oui donc elle est connecter : [ok]

Problematique :
    - C'est quoi role , comment se definit un role d'un utilisateur ??
        - mettre dans le session une key oe : "User Role"
        - annotation with authrole(role = "") :
        - Web xml contient le key du role :

    Role :
        possible oe anaky roa ilay role demandee :
        sur l'annotation le role sera separe par :
        annotation(role="admin&Utilisateur")
        \
    valeur an iny no anaran ilay variable anaty session : ok

    ilay fonction deconnecte : ok

    atao sprint 11 ilay singleton : 

sudo update-alternatives --config java
sudo update-alternatives --config javac

