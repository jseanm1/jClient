package jclient10;

public class Player {
    int player_no;
    int location[]; //location[0] : x , location[1] : y 
    int direction; // 0 : north, 1 : east, 2 : south, 3 : west
    int shot;
    int coins;
    int points;
    int health;
    boolean is_shot;
    boolean is_dead;
    
    public Player(){
        location = new int [2];
        shot = 0;
        is_dead = false;
    }
    
    public void set_health(int h){
        if(health>h)
            is_shot = true;
        else
            is_shot = false;
        health = h;
    }
}
