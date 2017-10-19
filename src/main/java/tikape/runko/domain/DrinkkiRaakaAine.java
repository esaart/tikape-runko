package tikape.runko.domain;

public class DrinkkiRaakaAine {

    private Drinkki drinkki;
    private RaakaAine raakaAine;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public DrinkkiRaakaAine(Drinkki drinkki, RaakaAine raakaAine, Integer jarjestys, String maara, String ohje) {
        this.drinkki = drinkki;
        this.raakaAine = raakaAine;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Drinkki getDrinkki() {
        return drinkki;
    }

    public void setDrinkki(Drinkki drinkki) {
        this.drinkki = drinkki;
    }

    public RaakaAine getRaakaAine() {
        return raakaAine;
    }

    public void setRaakaAine(RaakaAine raakaAine) {
        this.raakaAine = raakaAine;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

}
