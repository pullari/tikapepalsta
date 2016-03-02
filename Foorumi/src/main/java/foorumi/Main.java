package foorumi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:foorumi.db");
        KeskusteluDao kdao = new KeskusteluDao(db);
        ViestiDao vdao = new ViestiDao(db);
        AlueDao adao = new AlueDao(db);
        Keskustelu k = kdao.haeYksi("1", vdao);
        List<Viesti> viestit = k.getViestit();
        List<Alue> alueet = adao.haeKaikki();
        String t = "sahko";
        

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueet);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/keskustelu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestit);
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        get("/alue/" + t, (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestit);
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {
            String nimi = req.queryParams("linkki");
            return "Kerrotaan siitä tiedon lähettäjälle: " + nimi;
        });


        System.out.println("*****************");
        System.out.println(k.getNimi() + ":");

        for (Viesti v : k.getViestit()) {
            System.out.println("\t" + v.getViesti() + ":" + v.getNimimerkki() + ":" + v.getAikaleima());
        }

        for (Alue a : alueet) {
            System.out.println(a.getNimi() + ":" + a.getViesteja());
        }

    }
}
