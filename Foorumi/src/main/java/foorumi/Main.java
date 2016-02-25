package foorumi;

import java.sql.*;
import java.util.*;
import spark.*;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:foorumi.db");
        KeskusteluDao kdao = new KeskusteluDao(db);
        ViestiDao vdao = new ViestiDao(db);
        Keskustelu k = kdao.haeYksi("1", vdao);

        get("/viesti", (req, res) -> {
            String a = k.getNimi() + "<br/>" + "<br/>";
            for (Viesti v : k.getViestit()) {
                a += v.getViesti() + ":" + v.getAikaleima() + ":" + v.getNimimerkki() + "<br/>";
            }
            return a;
        });

        System.out.println("*****************");
        System.out.println(k.getNimi() + ":");

        for (Viesti v : k.getViestit()) {
            System.out.println("\t" + v.getViesti());
        }

    }
}
