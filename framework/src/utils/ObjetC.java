package utils;

public class ObjetC {
    String kls ;
    String[] values;
    public ObjetC() {
    }
    
    public ObjetC(String kls, String[] values) {
        this.kls = kls;
        this.values = values;
    }

    public String getKls() {
        return kls;
    }
    public void setKls(String kls) {
        this.kls = kls;
    }
    public String[] getValues() {
        return values;
    }
    public void setValues(String[] values) {
        this.values = values;
    }

    
}
