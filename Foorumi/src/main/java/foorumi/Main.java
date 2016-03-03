package foorumi;

<<<<<<< HEAD
import java.util.*;
import spark.*;
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
>>>>>>> 39f15b7b27d14b9ae411278c888b59f94012dc1a
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:foorumi.db");
<<<<<<< HEAD
        DAO dao = new DAO(db);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
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
                map.put("keskustelut", dao.haeAlueenKonvot(alue.getId()));
                return new ModelAndView(map, "keskustelut");
            }, new ThymeleafTemplateEngine());
            
            post("/" + alue.getNimi(), (req, res) -> {
                String nimi = req.queryParams("avaus");
                dao.uusiKonvo(alue, nimi);
                paivitaAlueet(dao.haeAlueet(), dao);
                paivitaViestit(dao.haeAlueenKonvot(alue.getId()), dao);
                HashMap map = new HashMap<>();
                map.put("keskustelut", dao.haeAlueenKonvot(alue.getId()));
                return new ModelAndView(map, "keskustelut");
            }, new ThymeleafTemplateEngine());
        }
    }
    
    public static void paivitaViestit(List<Keskustelu> konvot, DAO dao){
        for (Keskustelu konvo : konvot) {
            get("/" + konvo.getId(), (req, res) -> {
                HashMap map = new HashMap<>();
                map.put("viestit", dao.haeKeskustelunViestit(konvo.getId()));
                return new ModelAndView(map, "viestit");
            }, new ThymeleafTemplateEngine());
            
            post("/" + konvo.getId(), (req, res) -> {
                String nimi = req.queryParams("nimimerkki");
                String viesti = req.queryParams("viesti");
                dao.uusiViesti(konvo.getId(), nimi, viesti);
                HashMap map = new HashMap<>();
                map.put("viestit", dao.haeKeskustelunViestit(konvo.getId()));
                return new ModelAndView(map, "viestit");
            }, new ThymeleafTemplateEngine());
=======
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
>>>>>>> 39f15b7b27d14b9ae411278c888b59f94012dc1a
        }
    }
}
