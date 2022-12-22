package syntaxe;
// import syntaxe.*;
import bdd.*;

public class Operation{
    public String[] lesoperations(){
        String[] operations=new String[4];
        operations[0]="Projection";
        operations[1]="Difference";
        operations[2]="ProduitCartesien";
        operations[3]="Selection";
        return operations;
    }
    public Relation executeQuery(String requete) throws Exception{
        //donnees a rendre 
        String res="Opearion effectuée";
        Relation rel=new Relation("Resultat");
        String[] req=requete.split(" ");

        //check de la syntaxe
        String[] syntaxes=lesoperations();
        String mot=null;
        for(int i=0;i<syntaxes.length;i++){
            if(req[1].equalsIgnoreCase(syntaxes[i])==true){
                mot=req[1];
            }
        }
        if(mot==null){
            new Exception("Incorrect syntax");
        }

        //prend le nom de l'operation
        String operation=req[1];
        if(operation.equalsIgnoreCase("Projection")==true){
            Relation temp=Relation.toRelation(req[2]);
            rel=Relation.getProjection(temp,req[3], req[5]);
            Relation.toFichier(rel);
            // System.out.println("tonga eto");
            res="Projection effectuer et sauvegarder";
        } else if(operation.equalsIgnoreCase("Difference")==true){
            String[] relations=req[2].split("-");
            Relation r1=Relation.toRelation(relations[0]);
            Relation r2=Relation.toRelation(relations[1]);
            rel=Relation.getDifference(r1, r2, req[4]);
            Relation.toFichier(rel);
            // System.out.println(relations[0]+" "+relations[1]);
        } else if(operation.equalsIgnoreCase("ProduitCartesien")==true){
            String[] relations=req[2].split("x");
            Relation r1=Relation.toRelation(relations[0]);
            Relation r2=Relation.toRelation(relations[1]);
            
            rel=Relation.getProduitCartesien(r1, r2, req[4]);
            Relation.toFichier(rel);

            // res=relations[0];
        } else if(operation.equalsIgnoreCase("Selection")==true){
            Relation temp1=Relation.toRelation(req[2]);
            Relation temp=Relation.getSelection(temp1, req[3], req[5]);
            rel=Relation.getSelection(temp1, req[3], req[5]);
            Relation.toFichier(temp);
        }
        return rel;

        //Excecuter la requete
        // Object req=izy.getConstructor().newInstance();
        // Method execution= izy.getMethod("executeQuery",String.class);
        // execution.invoke(req,requete);
    }
    
}