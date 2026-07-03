package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.lang.reflect.Method;
import mg.itu.*;
public class Scannerrrs {
    public static ArrayList<String> ScannerController(String ChemineSource, String packageController){
        ArrayList<String> ListController = new ArrayList<>();
        String a = packageController.replace("/", ".");
        String chemine = ChemineSource;
        String chemineWPack = ChemineSource + "/" + a;
        Path cheminFichier = Paths.get(chemineWPack);
        //Path cheminFichier = Paths.get("/opt/tomcat/webapps/testFramework/WEB-INF/classes");
        try {
            Stream<Path> chemins = Files.walk(cheminFichier);
            chemins.forEach(chemin -> {
                if (!Files.isDirectory(chemin)){
                    String cheminString = chemin.toString().replace(chemin.getFileSystem().getSeparator(), ".");
                    String cheminVrais = cheminString.substring(chemine.length() + 1);
                    String CheminVraissansPointClasse = cheminVrais.substring(0, cheminVrais.length()-6);
                    Class<?> kilasy = null;
                    try {
                    kilasy = Class.forName(CheminVraissansPointClasse);
                    } catch (Exception e) {
                        
                    }
                    if(kilasy != null && kilasy.isAnnotationPresent(mg.itu.Controller.class)){
                        ListController.add(kilasy.getName());
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ListController;
    }
    public static ArrayList<ArrayList<String>> ScannerURLMapping(String ChemineSource, String packageController)throws Exception{
        ArrayList<ArrayList<String>> listePresentMapping = new ArrayList<>();
        ArrayList<String> ListController = new ArrayList<>();
        ListController = Scannerrrs.ScannerController(ChemineSource, packageController);
        for (int index = 0; index < ListController.size(); index++) {
            Class<?> kilasy = Class.forName(ListController.get(index));
            Method[] method = kilasy.getDeclaredMethods();
            for (int i = 0; i < method.length; i++) {
                ArrayList<String> listePresentMapping1 = new ArrayList<>();
                if (method[i].isAnnotationPresent(mg.itu.URLMapping.class)) {
                    listePresentMapping1.add(kilasy.getName());//Classe
                    listePresentMapping1.add(method[i].getName());//Methode
                    
                    URLMapping um = method[i].getAnnotation(mg.itu.URLMapping.class);
                    listePresentMapping1.add(um.value());//URL
                }
                listePresentMapping.add(listePresentMapping1);
            }
        }
        return listePresentMapping;
    }
}
