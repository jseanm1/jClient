package jclient10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    AIEngine AI;
    
    public Game(){
        AI = new AIEngine();
    }
    
    public void run(){
        connect();
        play();
    }
    
    public void connect(){
        try {
            BufferedWriter write;
            Socket serversoc = new Socket("127.0.0.1", 6000);
            write = new BufferedWriter(new OutputStreamWriter(serversoc.getOutputStream()));
            write.write("JOIN#");
            write.flush();
            write.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Test1_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(Test1_1.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server not found!!!");
            System.exit(5);
        }  
    }
    
    public void play(){
        String s, cmd="NO#";
        int i=0;
        while(true){    
            try {
                // Listen to the port
                BufferedReader read;
                ServerSocket clientserversoc = new ServerSocket(7000);
                Socket clientsoc = clientserversoc.accept();
                read = new BufferedReader(new InputStreamReader(clientsoc.getInputStream()));
                s = read.readLine();
                read.close();
                
                // Pass the update to the AIEngine and get the response
                try{
                    cmd = AI.nextMove(s);
                }catch(Exception e){
                    cmd = "NO#";
                }
                
                // If there is a message to server, send it
                if(!cmd.equals("NO#")){
                    BufferedWriter write;
                    Socket serversoc = new Socket("127.0.0.1", 6000);
                    write = new BufferedWriter(new OutputStreamWriter(serversoc.getOutputStream()));

                    write.write(cmd);
                    write.flush();
                    write.close();
                }
            
            } catch (IOException ex) {
//                System.out.println("IO Exception: "+ex);
//                System.out.println("Exception caught at Game.play()");
            }
            i++;
        }
    }
}
