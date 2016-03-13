package foorumi;

import java.util.*;
import spark.*;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            System.out.println("löytyi");
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 

        Database db = new Database(jdbcOsoite);
        DAO dao = new DAO(db);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            paivitaAlueet(dao.haeAlueet(), dao);
            map.put("alueet", dao.haeAlueet());
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            dao.uusiAlue(nimi);
            paivitaAlueet(dao.haeAlueet(), dao);
            HashMap map = new HashMap<>();
            map.put("alueet", dao.haeAlueet());
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());
        
        paivitaAlueet(dao.haeAlueet(), dao);
        paivitaViestit(dao.haeKonvot(), dao);
    }
    
    public static void paivitaAlueet(List<Alue> alueet, DAO dao) throws Exception{
        for (Alue alue : alueet) {
            get("/" + alue.getNimi(), (req, res) -> {
                HashMap map = new HashMap<>();
                String sivu = req.queryParams("sivu");
                map.put("keskustelut", dao.haeAlueenKonvotOffset(alue.getId(), parseSivu(sivu)));
                return new ModelAndView(map, "keskustelut");
            }, new ThymeleafTemplateEngine());
            
            post("/" + alue.getNimi(), (req, res) -> {
                String nimi = req.queryParams("avaus");
                dao.uusiKonvo(alue, nimi);
                paivitaViestit(dao.haeKonvot(), dao);
                HashMap map = new HashMap<>();
                map.put("keskustelut", dao.haeAlueenKonvotOffset(alue.getId(), 0));
                return new ModelAndView(map, "keskustelut");
            }, new ThymeleafTemplateEngine());
        }
    }
    
    public static void paivitaViestit(List<Keskustelu> konvot, DAO dao){
        for (Keskustelu konvo : konvot) {
            get("/" + konvo.getId(), (req, res) -> {
                HashMap map = new HashMap<>();
                String sivu = req.queryParams("sivu");
                map.put("viestit", dao.haeKeskustelunViestitOffset(konvo.getId(), parseSivu(sivu)));
                return new ModelAndView(map, "viestit");
            }, new ThymeleafTemplateEngine());
            
            post("/" + konvo.getId(), (req, res) -> {
                String nimi = req.queryParams("nimimerkki");
                String viesti = req.queryParams("viesti");
                dao.uusiViesti(konvo.getId(), nimi, viesti);
                HashMap map = new HashMap<>();
                map.put("viestit", dao.haeKeskustelunViestitOffset(konvo.getId(), 0));
                return new ModelAndView(map, "viestit");
            }, new ThymeleafTemplateEngine());
        }
    }
    
    public static int parseSivu(String parse){
        int sivu = 1;
        try{
            sivu = Integer.parseInt(parse);
        }catch(Exception e){
        }
        return sivu - 1;
    }
}
