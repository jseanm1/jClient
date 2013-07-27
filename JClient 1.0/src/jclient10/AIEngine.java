package jclient10;

public class AIEngine {
    String global_update;
    String coin_pile;
    String health_pack;
    String terrain;
    int ln, wt;
    int my_no;
    
    public String nextMove(String s){
        String cmd = "SHOOT#";
        setParameter(s);
                
        return cmd;
    }
    
    private void setParameter(String s){
        if(s.charAt(0)=='I'){
            //Initialize the game
            initialize(s);
        }
    }
    
    private void initialize(String s){
        ln = wt = 20;
        String[] temp = s.split(":");
        my_no = Integer.parseInt(temp[1].charAt(temp[1].length())-1+"");
        
    }
}
