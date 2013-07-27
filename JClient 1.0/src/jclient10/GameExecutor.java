package jclient10;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameExecutor {
    
    public void execute(){
        try{
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("debug\\lk.ac.mrt.cse.pc11.exe");
            InputStream io = p.getInputStream();
            while(true){
                System.out.println(io);
            }
            
        }catch(Exception e){
            Logger.getLogger(GameExecutor.class.getName()).log(Level.SEVERE, null, e);
            
        }
    }
}
