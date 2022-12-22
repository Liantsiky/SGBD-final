package syntaxe;
import java.io.*;
import java.util.*;

import bdd.Relation;
public class Show {
    public Relation executeQuery(String requete)throws Exception{
        Relation res=new Relation();
        
        //prend le nom de la table
        String[] req=requete.split(" ");
        String table= req[2];
        //transforme en Relation
        res=Relation.toRelation(table);

        return res;
    }

    //fonction retourne la table et les arguments correspondants a la recherche
    public static String getTableandArgument(String seek)throws Exception{
        String result=null;
        // retire les tables existantes du fichier "tables.txt"
        FileReader fichier=new FileReader("tables.txt");
        BufferedReader re=new BufferedReader(fichier);
        String ligne= re.readLine();
        String[] allLigne= ligne.split(";;");

        //rechercher la table
        for(int i=0;i<allLigne.length;i++){
            String [] check=allLigne[i].split("/");
            if(check[0].equals(seek)==true){
                //retourne les valeurs et les varibles de la table correspondante
                result=check[1];
            }
        }
        if(result==null){
            new Exception("La table que vous cherchez n'existe pas");
        }
        return result;
    }
}
