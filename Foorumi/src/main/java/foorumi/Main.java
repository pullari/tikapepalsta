package foorumi;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:foorumi.db");
        KeskusteluDao kdao = new KeskusteluDao(db);
        ViestiDao vdao = new ViestiDao(db);
        Keskustelu k = kdao.haeYksi("1", vdao);
        
        System.out.println("*****************");
        System.out.println(k.getNimi() + ":");
        
        for (Viesti v : k.getViestit()) {
            System.out.println("\t" + v.getViesti());
        }
        
        
        
    }
}
