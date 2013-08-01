package jclient10;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test1_2 {
        
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
            Logger.getLogger(Test1_1.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server not found!!!");
            System.exit(5);
        }  
    }
    
    public void play(){
        String s, cmd;
        int i=0;
        while(true){    
            try {
                BufferedReader read;
                ServerSocket clientserversoc = new ServerSocket(7000);
                Socket clientsoc = clientserversoc.accept();
                read = new BufferedReader(new InputStreamReader(clientsoc.getInputStream()));
                s = read.readLine();
                System.out.println(s); // do the job, I just print out the msg            
                read.close();
                
                /*BufferedWriter write;
                Socket serversoc = new Socket("127.0.0.1", 6000);
                write = new BufferedWriter(new OutputStreamWriter(serversoc.getOutputStream()));
                
                cmd = testCommand(s);
                //cmd = engine.nextMove(s);
                write.write(cmd);
                write.flush();
                write.close();*/
            
            } catch (IOException ex) {
                //Logger.getLogger(Test1_1.class.getName()).log(Level.SEVERE, null, ex);
                //System.exit(0);
            }
            i++;
        }
    }
    
    public void run(){
        connect();
        play();
    }
    
    private String testCommand(String s){
        Scanner scan = new Scanner(System.in);
        s = null;
        s = scan.nextLine();
                
        if(s.equals("w")){
             s = "UP#";
        }
        else if(s.equals("s")){
             s = "DOWN#";
        }
        else if(s.equals("a")){
             s = "LEFT#";
        }
        else if(s.equals("d")){
             s = "RIGHT#";
        }
        else if(s.equals(" ")){
             s = "SHOOT#";
        }
        else if(s.equals("p")){
            System.exit(0);
        }
        //d
                
        return s;
    }
}
