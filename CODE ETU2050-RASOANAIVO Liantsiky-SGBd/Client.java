package affichage;

import syntaxe.*;
import verification.*;

import java.net.*;
import java.util.Scanner;

import bdd.Relation;

import java.io.*;
public class Client extends Thread{
    Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client(Socket socket){
        this.setSocket(socket);
    }
    public Client(){}

    public static void Afficher(Relation rel){
        String al="";
        for(int i=0;i<rel.getAttributs().size();i++){
            al=al+"|   "+rel.getAttribut(i)+"  ";
            
        }
        System.out.println(al);
        for(int l=0;l<rel.getLignes().size();l++){
            String af="";
            for(int j=0;j<rel.getAttributs().size();j++){
                af=af+"|    "+rel.getLignes().get(l).get(j)+"   ";
               
            }
            System.out.println(af);
        }
    }
    public static void main(String[] args)throws IOException{
        Client c=new Client(new Socket("localhost",2051));
        c.start();

        DataOutputStream donnee= new DataOutputStream(c.getSocket().getOutputStream());
        while(true){
            System.out.println("Manomboka->");
            Scanner sc=new Scanner(System.in);
            String req=sc.nextLine();
            //prend le mot cle
            String [] cle=req.split(" ");
        
            donnee.writeUTF(req);
            donnee.flush();
        
            if(cle[0].equalsIgnoreCase("Insert")==true){
                        // String envoi="Insert effectuer";
                        DataInputStream sendres=new DataInputStream(c.getSocket().getInputStream());
                        System.out.println(sendres.readUTF());
                
                    } else if(cle[0].equalsIgnoreCase("Create")==true){
                            // String envoi="Create effectuer";
                    DataInputStream sendres=new DataInputStream(c.getSocket().getInputStream());
                    System.out.println(sendres.readUTF());
        
            } else{
                ObjectInputStream Input  = new ObjectInputStream(c.getSocket().getInputStream());
                Relation relation = null;
                try {
                    Object obj = Input.readObject();
                    if(obj!=null){
                        relation= (Relation)obj;
                        Afficher(relation);
                    }else{
                        System.out.println("Revoyez votre syntaxe");
                    }
        
                } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                }
            
                }
            }
         }
    }