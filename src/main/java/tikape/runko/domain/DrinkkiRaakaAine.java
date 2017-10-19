/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author makisann
 */
public class DrinkkiRaakaAine {
    private Integer drinkki_id;
    private Integer raakaaine_id;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public DrinkkiRaakaAine(Integer drinkki_id, Integer raakaaine_id, Integer jarjestys, String maara, String ohje) {
        this.drinkki_id = drinkki_id;
        this.raakaaine_id = raakaaine_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getDrinkki_id() {
        return drinkki_id;
    }

    public Integer getRaakaaine_id() {
        return raakaaine_id;
    }

    public void setDrinkki_id(Integer drinkki_id) {
        this.drinkki_id = drinkki_id;
    }

    public void setRaakaaine_id(Integer raakaaine_id) {
        this.raakaaine_id = raakaaine_id;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
    
    
}
