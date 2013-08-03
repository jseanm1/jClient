package jclient10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    ServerSocket serverSocket = null; 
    Socket clientSocket = null;
    
    public boolean start(){
        try{
            serverSocket = new ServerSocket(7001);
        }catch(IOException e){
            System.err.println("Couldn't listen on port 7001 "+e);
            return false;
        }
        
        try{
            clientSocket = serverSocket.accept();
            return true;
        }catch(IOException e){
            System.err.println("Accept Failed");
            return false;
        }        
    
    }
    
    public void write(){
        int i=0;
        while(true){
            if(i%1000==0){
                try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                bw.write("Hello");
                bw.flush();
                bw.close();
                }catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            i++;
        }
    }
}
