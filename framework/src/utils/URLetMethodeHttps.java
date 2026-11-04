package utils;

import java.util.Objects;

public class URLetMethodeHttps {
    String Url;
    String methode;
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
    public String getMethode() {
        return methode;
    }
    public void setMethode(String methode) {
        this.methode = methode;
    }
    @Override
    public boolean equals(Object object){
        URLetMethodeHttps ob = (URLetMethodeHttps) object;
        boolean bool = true;
        if (!this.getUrl().equals(ob.getUrl())) {
            bool = false;
        }if (!this.getMethode().equals(ob.getMethode())) {
            bool = false;
        }
        return bool;
    }
    @Override
    public int hashCode() {
       return Objects.hash(this.Url, this.methode);
    }
}
