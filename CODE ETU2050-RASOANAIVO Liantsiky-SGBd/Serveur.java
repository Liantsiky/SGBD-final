package affichage;

import syntaxe.*;
import verification.*;

import java.net.*;

import bdd.Relation;

import java.io.*;
public class Serveur implements Serializable{
    String serveur;
    String port;
    Socket socket;

    public String getServeur(){return this.serveur;}
    public String getPort(){return this.port;}
    public Socket getSocket(){return this.socket;}

    public void setServeur(String ser){this.serveur=ser;}
    public void setPort(String ser){this.port=ser;}
    public void setSocket(Socket oui){this.socket=oui;}

    public Serveur(Socket oui){
        this.setSocket(oui);
    }

    public void alefa(){
        try {
        DataInputStream donnee= new DataInputStream(this.getSocket().getInputStream());
        String resultat=donnee.readUTF();
        System.out.println("Votre requete : "+resultat);
    
        String cle[]=resultat.split(" ");

        Relation res =Traitement.getTraitement(resultat);
            if(cle[0].equalsIgnoreCase("Insert")==true){
                String envoi="Insert effectuer";
                DataOutputStream sendres=new DataOutputStream(this.getSocket().getOutputStream());
                sendres.writeUTF(envoi);
                sendres.flush();
            } else if(cle[0].equalsIgnoreCase("Create")==true){
                String envoi="Create effectuer";
                DataOutputStream sendres=new DataOutputStream(this.getSocket().getOutputStream());
                sendres.writeUTF(envoi);
                sendres.flush();
            } else{
                ObjectOutputStream output  = new ObjectOutputStream(this.getSocket().getOutputStream());
                output.writeObject(res);
                output.flush();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public static void main(String[] args)throws IOException{
        ServerSocket serv=new ServerSocket(2051);
        Serveur ser=new Serveur(serv.accept());

        // Relation res=new Relation();
        while(true){
            ser.alefa();
        }
        
    }
}