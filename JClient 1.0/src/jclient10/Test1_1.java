package jclient10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test1_1 {
        
    public void testConnection(){
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

        int i=0;
        while(true){    
        try {
            //BufferedWriter br = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader read;
            ServerSocket clientserversoc = new ServerSocket(7000);
            Socket clientsoc = clientserversoc.accept();
            read = new BufferedReader(new InputStreamReader(clientsoc.getInputStream()));
            String s = read.readLine();
            System.out.println(s); // do the job, I just print out the msg
            //br.append(s);
            read.close();
            //999999999999999999
            /*BufferedWriter write;
            Socket serversoc = new Socket("127.0.0.1", 6000);
            write = new BufferedWriter(new OutputStreamWriter(serversoc.getOutputStream()));
            read.close();
            if(i%100000==0){
                write.write("RIGHT#");
                write.flush();
            }
            else if(i%100000==5000){
                write.write("LEFT#");
                write.flush();
            }*/
            i++;
        } catch (IOException ex) {
            //Logger.getLogger(Test1_1.class.getName()).log(Level.SEVERE, null, ex);
            //System.exit(0);
        }
        }
    }
}
       
