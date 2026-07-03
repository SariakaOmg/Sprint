package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                    listePresentMapping1.add(um.methode());
                }
                listePresentMapping.add(listePresentMapping1);
            }
        }
        return listePresentMapping;
    }
    public static Map<URLetMethodeHttps, ClasseMethodeMap> MettreDansMap(ArrayList<ArrayList<String>> listePresentMapping)throws Exception{
        Map<URLetMethodeHttps, ClasseMethodeMap> listeUrMap3 = new HashMap<>();

        for (int index = 0; index < listePresentMapping.size(); index++) {
               ClasseMethodeMap cm = new ClasseMethodeMap();
               cm.setKilasy(Class.forName(listePresentMapping.get(index).get(0)));
               cm.setNomMethode(listePresentMapping.get(index).get(1));
               URLetMethodeHttps urLetMethodeHttps = new URLetMethodeHttps();
               urLetMethodeHttps.setMethode(listePresentMapping.get(index).get(3));
               urLetMethodeHttps.setUrl(listePresentMapping.get(index).get(2));
               if(listeUrMap3.get(urLetMethodeHttps) != null){
                throw new Exception("Deux Methodes ayant le meme URL ");
               }else{
               listeUrMap3.put(urLetMethodeHttps, cm);
               }
            }

        return listeUrMap3;
    }
}
