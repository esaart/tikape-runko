package tikape.runko.domain;

public class DrinkkiRaakaAine {

    private Drinkki drinkki;
    private RaakaAine raakaAine;
    private Integer jarjestys;
    private Double maara;
    private String yksikko;
    private String ohje;

    public DrinkkiRaakaAine(Drinkki drinkki, RaakaAine raakaAine, Integer jarjestys, Double maara, String yksikko, String ohje) {
        this.drinkki = drinkki;
        this.raakaAine = raakaAine;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.yksikko = yksikko;
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

    public Double getMaara() {
        return maara;
    }

    public void setMaara(Double maara) {
        this.maara = maara;
    }

    public String getYksikko() {
        return yksikko;
    }

    public void setYksikko(String yksikko) {
        this.yksikko = yksikko;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

}
