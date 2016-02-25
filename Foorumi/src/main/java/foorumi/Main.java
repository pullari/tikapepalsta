package foorumi;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:foorumi.db");
        KeskusteluDao kdao = new KeskusteluDao(db);
        ViestiDao vdao = new ViestiDao(db);
        AlueDao adao = new AlueDao(db);
        Keskustelu k = kdao.haeYksi("1", vdao);
        List<Alue> alueet = adao.haeKaikki();
        
        System.out.println("*****************");
        System.out.println(k.getNimi() + ":");
        
        for (Viesti v : k.getViestit()) {
            System.out.println("\t" + v.getViesti() + ":" + v.getNimimerkki()+ ":" + v.getAikaleima());
        }
        
        for (Alue a : alueet) {
            System.out.println(a.getNimi() + ":" + a.getViesteja());
        }
        
        
        
    }
}
