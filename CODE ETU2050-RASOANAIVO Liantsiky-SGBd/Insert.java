package syntaxe;
import java.io.*;
import bdd.Relation;
public class Insert{
    
    public Relation executeQuery(String requete)throws Exception{
        Relation res=null;
        //prend le nom de la table

        String[] phrase=requete.split(" ");
        String table=phrase[2];

        //creer un fichier pour sauvegarder au nom de la table
        FileOutputStream save=new FileOutputStream(table+".txt",true);

        //prend les valeurs à inserer
        String valeurs=phrase[3];
        String[] v1=valeurs.split("/");
        String sauvegarder= v1[1]+";;"; //;; distingue chaque insertion de valeur
        
        //sauvergarde
        save.write(sauvegarder.getBytes());
        save.close();
        return res;
    }
}
//SYNTAXE : Insert in *nom de la table* /*les valeurs */