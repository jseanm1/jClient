package jclient10;

public class Player {
    int player_no;
    int location[]; //location[0] : x , location[1] : y 
    int direction; // 0 : north, 1 : east, 2 : south, 3 : west
    int is_shot;
    int coins;
    int points;
    int health;
    
    public Player(){
        location = new int [2];
        is_shot = 0;
    }
}
