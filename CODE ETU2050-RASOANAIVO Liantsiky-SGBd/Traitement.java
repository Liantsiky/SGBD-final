package verification;
import java.lang.reflect.*;

import bdd.Relation;
public class Traitement{

    public static String [] getSyntaxe(){
        String[] syntaxe=new String [4];
        syntaxe[0]="Insert";
        syntaxe[1]="Create";
        syntaxe[2]="Show";
        syntaxe[3]="Operation";
        return syntaxe;
    }

    public static Relation getTraitement (String requete)throws Exception{
        Relation res=new Relation();
        //prendre le mot cle
        String[] cle= requete.split(" ");
        String[] syntaxes=getSyntaxe();
        String mot=null;

        //verification de la syntaxe
        for(int i=0;i<syntaxes.length;i++){
            if(cle[0].equalsIgnoreCase(syntaxes[i])==true){
                mot=cle[0];
            }
        }
        if(mot==null){
            new Exception("Incorrect syntax");
        }

        //rechercher la classe correspondante
        Class izy=Class.forName("syntaxe."+mot);

        //Excecuter la requete
        Object req=izy.getConstructor().newInstance();
        Method execution= izy.getMethod("executeQuery",String.class);
        res=(Relation)execution.invoke(req,requete);
        return res;
    }
}