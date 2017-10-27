package tikape.runko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.DrinkkiRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/drinkit.db");
        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaaineDao = new RaakaAineDao(database);
        DrinkkiRaakaAineDao drinkkiraakaaineDao = new DrinkkiRaakaAineDao(database);
        ArrayList<String> yksikot = new ArrayList<>();
        yksikot.addAll(Arrays.asList("l", "dl", "cl", "kpl", "siivu"));

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
            Drinkki drinkki = drinkkiDao.findOne(Integer.parseInt(req.params("id")));
            map.put("drinkki", drinkki);
            List<DrinkkiRaakaAine> dras = drinkkiraakaaineDao.getByDrinkki(drinkki);

            Collections.sort(dras, (DrinkkiRaakaAine o1, DrinkkiRaakaAine o2)
                    -> Double.compare(o1.getJarjestys(), o2.getJarjestys()));

            map.put("drinkkiraakaaineet", dras);

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

        get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("raakaaineet", raakaaineDao.findAll());
            map.put("yksikot", yksikot);

            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());

        post("/lisays/poista/:id", (req, res) -> {
            drinkkiDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/lisays");
            return "";
        });

        post("/ainekset/poista/:id", (req, res) -> {
            System.out.println(req.params("id"));

            raakaaineDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/ainekset");
            return "";
        });

        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "RaakaAineet");
        }, new ThymeleafTemplateEngine());

        post("/lisays", (req, res) -> {
            Drinkki drinkki = new Drinkki(null, req.queryParams("nimi"));
            drinkkiDao.saveOrUpdate(drinkki);

            res.redirect("/lisays");
            return "";
        });

        post("/lisays/aine", (req, res) -> {
            Drinkki drinkki = drinkkiDao.findOne(Integer.parseInt(req.queryParams("drinkkiId")));
            RaakaAine raakaAine = raakaaineDao.findOne(Integer.parseInt(req.queryParams("raakaaineId")));
            DrinkkiRaakaAine dra = new DrinkkiRaakaAine(drinkki, raakaAine, 
                    Integer.parseInt(req.queryParams("jarjestys")),
                    Double.parseDouble(req.queryParams("maara")), 
                    req.queryParams("yksikko"), req.queryParams("ohje"));
            drinkkiraakaaineDao.saveOrUpdate(dra);
            
            System.out.println("");
            
            res.redirect("/lisays");
            return "";
        });

        post("/ainekset", (req, res) -> {
            RaakaAine raakaAine = new RaakaAine(null, req.queryParams("nimi"));
            raakaaineDao.saveOrUpdate(raakaAine);

            res.redirect("/ainekset");
            return "";
        });

//        post("/drinkit", (req,res) -> {
//            )
//            
//        })
    }
}
