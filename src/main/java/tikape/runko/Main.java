package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/drinkit.db");
        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaaineDao = new RaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());
        
        get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());
        
        post("/poista", (req, res) -> {
            drinkkiDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/lisays");
            return "";
            
        });
        
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaaineDao.findAll());
            for (RaakaAine raakaaine : raakaaineDao.findAll()) {
                System.out.println(raakaaine.getNimi());
                
            }
            
            return new ModelAndView(map, "RaakaAineet");
            }, new ThymeleafTemplateEngine());
        
        
        post("/lisays", (req, res) -> {
            Drinkki drinkki = new Drinkki(null, req.queryParams("nimi"));
            drinkkiDao.saveOrUpdate(drinkki);

            res.redirect("/lisays");
            return "";
        });
        
//        post("/drinkit", (req,res) -> {
//            )
//            
//        })
    }
}
