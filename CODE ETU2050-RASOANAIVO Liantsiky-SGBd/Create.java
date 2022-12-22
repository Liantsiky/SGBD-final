package syntaxe;

import java.io.*;

import bdd.Relation;


public class Create {
    public Relation executeQuery(String requete)throws Exception{
        //creation du fichier stockage liste de toutes les tables
        Relation res=null;
        FileOutputStream saving=new FileOutputStream("tables.txt",true);
        
        //prendre le nom de la table
        String [] req= requete.split(" ");
        //prendre les valeurs
        String [] values=requete.split("/");
        String table=req[2]+"/"+values[1]+"/"+";;";
        saving.write(table.getBytes());
        saving.close();
        return res;
    }
}

//SYNTAXE : CREATE TABLE *nom de la table* /*les types puis les variables */