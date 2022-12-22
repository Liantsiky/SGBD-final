package bdd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.annotation.processing.FilerException;
import javax.naming.spi.DirStateFactory.Result;
import javax.print.DocFlavor.READER;
public class Relation implements Serializable{
    String nom;
    Vector <String> attributs;
    Vector <Vector <String>> lignes;

    public String getNom(){return this.nom;}
    public Vector <String>  getAttributs(){return this.attributs;}
    public String getAttribut(int i){return this.attributs.get(i);}
    public Vector <Vector<String>> getLignes(){return this.lignes;}
    public Vector <String> getLigne(int i){return this.lignes.get(i);}
    public String getCase(int ligne,int colonne){return this.getLigne(ligne).get(colonne);}

    public void setNom(String p){this.nom=p;}
    public void setAttributs(Vector <String> a){this.attributs=a;}
    public void setLignes(Vector <Vector <String>> l){this.lignes=l;}

    public Relation(String str){
        this.setNom(str);
    }
    public Relation(){}

    // enleve les doublons
    public static Vector<Vector<String>> outDoublon(Vector<Vector<String>> atraiter){
        // donnees
            Vector <Vector <String>> result=new Vector<Vector<String>>();
        // traitement
            for(int i=0;i<atraiter.size();i++){
                if(result.contains(atraiter.get(i))==false){
                    result.add(atraiter.get(i));
                }
            }
        // resultats
            return result;
        }

    //prend une relation et la transforme en fichier
    public static void toFichier(Relation atransformer)throws Exception{
        String donnees="";
        String table=atransformer.getNom()+"/";
    // creation des fichiers
        FileOutputStream newtable= new FileOutputStream(atransformer.getNom()+".txt",true);
    // entree des donnees dans le String
        for(int i=0;i<atransformer.getLignes().size();i++){
            for(int j=0;j<atransformer.getAttributs().size();j++){
                    if(j==atransformer.getAttributs().size()-1){
                        donnees=donnees+atransformer.getCase(i, j);
                    }else{
                        donnees=donnees+atransformer.getCase(i, j)+",";
                    }
            }
            donnees=donnees+";;";
        }
    // entree des attributs
        FileOutputStream newattr= new FileOutputStream("tables.txt",true);
        for(int k=0;k<atransformer.getAttributs().size();k++){
            if(k==atransformer.getAttributs().size()-1){
                table=table+atransformer.getAttribut(k)+"/";
            }else{
                table=table+atransformer.getAttribut(k)+",";
            }
        }
        table=table+";;";
    // sauvegarder
        newtable.write(donnees.getBytes());
        newattr.write(table.getBytes());
        newtable.close();
        newattr.close();

    }

    //prend dans la base puis transforme en relation
    public static Relation toRelation(String fichier)throws Exception{
        Relation res=new Relation("fichier");
        //pour les attributs
    // donnees
        Vector <String> attrb=new Vector<String>();
        String lesattr=null;
    // extraction du fichier
        FileReader fi= new FileReader("tables.txt");
        BufferedReader lecti=new BufferedReader(fi);
        String line= lecti.readLine();
        String [] allAttr=line.split(";;");
    // rechercher la ligne correspondante
        for(int k=0;k<allAttr.length;k++){
           String theligne[]=allAttr[k].split("/");
           if(theligne[0].compareToIgnoreCase(fichier)==0){
                lesattr=theligne[1];
           }
        }
    //spliter et prendre les noms des attributs
    String attributs[]=lesattr.split(",");
    // System.out.println("ALavany: "+attributs.length);
        for(int l=0;l<attributs.length;l++){
            // System.out.println(attributs[l]);
            String ajout[]=attributs[l].split(" ");
            
                attrb.add(ajout[0]);
                // attrb.add(ajout[1]);
        }

        //pour les lignes
    //  donnees
        Vector <Vector <String>> resulat=new Vector<Vector<String>>();
        
    //  extraction du fichier
        FileReader fich= new FileReader(fichier+".txt");
        BufferedReader lect=new BufferedReader(fich);
        String ligne= lect.readLine();
        String [] allLigne=ligne.split(";;");
        System.out.println(allLigne.length);
    //  Insertion dans le vecteur
        for(int i=0;i<allLigne.length;i++){
            String uneLigne[]=allLigne[i].split(",");
            Vector <String> temp=new Vector<String>();
            for(int j=0;j<uneLigne.length;j++){
                temp.add(uneLigne[j]);
            }
            resulat.add(temp);
        }
    //  Set les lignes
        res.setAttributs(attrb);
        res.setLignes(resulat);
        lect.close();
        lecti.close();
        return res;
    }

    //Projection 
        public static Relation getProjection(Relation r1,String colonne,String Nom){
        // donnee
            Vector <Integer> indices= new Vector<Integer>(); //stocker l'indice des colonnes a projeter
            Relation resultat= new Relation(Nom);
            Vector <Vector<String>> resLignes=new Vector<Vector<String>>();
            Vector <String> newAttributs=new Vector<String>();
            String []colonnes=colonne.split(",");

        // prend l'indice des colonnes a projete
            for(int i=0;i<r1.getAttributs().size();i++){
                for(int k=0;k<colonnes.length;k++){
                    if(r1.getAttribut(i).equalsIgnoreCase(colonnes[k])==true){
                        // System.out.println(i);
                        indices.add(i);
                    }
                }
            }
        // insertion
            //les lignes
            for(int j=0;j<r1.getLignes().size();j++){
                Vector<String> temp=new Vector<String>();
                for(int k=0;k<indices.size();k++){
                    temp.add(r1.getCase(j, indices.get(k)));
                }
                resLignes.add(temp);
            }
            //les Attributs
            for(int l=0;l<indices.size();l++){
                newAttributs.add(r1.getAttribut(indices.get(l)));
            }
        // set
            resultat.setAttributs(newAttributs);
            resultat.setLignes(outDoublon(resLignes));
            resultat.setNom(Nom);
            return resultat;
        }

    //Produit cartesien
        public static Relation getProduitCartesien(Relation r1,Relation r2,String NameNewRelation){
        // donnees
            Relation resultat=new Relation(NameNewRelation);
            Vector <Vector<String>> lignesFinal=new Vector<Vector<String>>();
        // operation
            // attributs
                Vector <String> newAttributs=new Vector<String>();
                for(int k=0;k<r1.getAttributs().size();k++){
                    newAttributs.add(r1.getAttribut(k));
                }
                for(int l=0;l<r2.getAttributs().size();l++){
                    newAttributs.add(r2.getAttribut(l));
                }
            // lignes
            for(int i=0;i<r1.getLignes().size();i++){
                for(int j=0;j<r2.getLignes().size();j++){
                    Vector<String> temp=new Vector<String>();
                    temp.addAll(r1.getLigne(i));
                    temp.addAll(r2.getLigne(j));
                    lignesFinal.add(temp);
                    // System.out.println(i+" "+j);
                }
                // lignesFinal.add(r2.getLigne(j));
                // lignesFinal.add(temp);
            }
        // set
            resultat.setAttributs(newAttributs);
            resultat.setLignes(outDoublon(lignesFinal));
        return resultat;
        }
    //Difference
    public static Relation getDifference(Relation r1,Relation r2,String nom)throws Exception{
    // donnees
        Relation resultat= new Relation(nom);
        Vector <Vector<String>> newLignes=new Vector<Vector<String>>();
        Vector <String> newAttributs=new Vector<String>();
    // check nombre de colonnes
        if(r1.getAttributs().size()!=r2.getAttributs().size()){
            throw new Exception("Tsy mitovy le abehany");
        } else{
    // comparaison et insertion
        // lignes
            for(int i=0;i<r1.getLignes().size();i++){
                //for(int k=0;k<r2.getLignes().size();k++){
                    if(r1.checkRahao(r1.getLigne(i), r2)==false){
                        newLignes.add(r1.getLigne(i));
                    }
                //}
            }
        
        // attributs
            for(int j=0;j<r1.getAttributs().size();j++){
                newAttributs.add(r1.getAttribut(j));
            }
        }
    // set
        resultat.setAttributs(newAttributs);
        resultat.setLignes(outDoublon(newLignes));
        return resultat;
    }

    //si une ligne est dans cette relation
    public boolean checkRahao(Vector <String> check,Relation r2){
        boolean res=false;
        for(int i=0;i<r2.getLignes().size();i++){
            if(check.containsAll(r2.getLigne(i))==true){
                res=true;
            }
        }
        return res;
    }

    //selection
    public  static  Relation getSelection(Relation r1, String condition,String nom){
        // donnee
            Relation resultat= new Relation(nom);
            Vector <Vector<String>> newLignes=new Vector<Vector<String>>();
            Vector <String> newAttributs=new Vector<String>();
            int indice=0;
            String[] cond= condition.split("=");
        // prend l'indice de la colonne reference
            for(int i=0;i<r1.getAttributs().size();i++){
                if(r1.getAttribut(i).equalsIgnoreCase(cond[0])==true){
                    indice=i;
                }
            }
        // prend les attributs 
            for(int k=0;k<r1.getAttributs().size();k++){
                newAttributs.add(r1.getAttribut(k));
            }
        // comparaison
            for(int j=0;j<r1.getLignes().size();j++){
                if(r1.getCase(j, indice).equalsIgnoreCase(cond[1])==true){
                    newLignes.add(r1.getLigne(j));
                }
            }
        // set
            resultat.setLignes(newLignes);
            resultat.setAttributs(newAttributs);
            return resultat;
        }
        
}
