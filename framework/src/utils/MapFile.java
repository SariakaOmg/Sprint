package utils;

public class MapFile {
    String nomFichier;
    String extension;
    byte[] contenu;
    public String getNomFichier() {
        return nomFichier;
    }
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public byte[] getContenu() {
        return contenu;
    }
    public void setContenu(byte[] contenu) {
        this.contenu = contenu;
    }
    public MapFile() {
    }
    public MapFile(String nomFichier, String extension, byte[] contenu) {
        this.nomFichier = nomFichier;
        this.extension = extension;
        this.contenu = contenu;
    }
}