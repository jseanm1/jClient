package jclient10;

import java.util.ArrayList;

public class AIEngine {
    String nxtmv;
    String global_update;
    String coin_pile;
    String health_pack;
    int[][] terrain;
    Player[] players;
    Brick[] bricks;
    ArrayList <CoinPile> coinpiles;
    ArrayList <LifePack> lifepacks;
    /*
     * terrain = 0 : nothing
     * terrain = 1 : brick
     * terrain = 2 : stone
     * terrain = 3 : water
     * terrain = 4 : tank
     * terrain = 5 : coin pile
     * terrain = 6 : life pack
    */
    int ln, ht; //ln : x, ht : y
    int my_no;
    
    public String nextMove(String s){
        setParameter(s);
               
        return nxtmv;
    }
    
    private void setParameter(String s){
        if(s.equals("OBSTACLE#")){
            
        }
        else if(s.equals("CELL_OCCUPIED#")){
            
        }
        else if(s.equals("DEAD#")){
            
        }
        else if(s.equals("TOO_QUICK#")){
            
        }
        else if(s.equals("INVALID_CELL#")){
            
        }
        else if(s.equals("GAME_HAS_FINISHED#")){
            
        }
        else if(s.equals("GAME_NOT_STARTED_YET#")){
            
        }
        else if(s.equals("NOT_A_VALID_CONTESTANT#")){
            
        }
        else if(s.charAt(0)=='I'){
            //Initialize the game
            initialize(s);
        }
        else if(s.charAt(0)=='S'){
            //Server reply with all players joined up to that time
            add_players(s);
        }
        else if(s.charAt(0)=='G'){
            //Global update
            global_update(s);
        }
        else if(s.charAt(0)=='C'){
            //New coil pile
            new_coin_pile(s);
        }
        else if(s.charAt(0)=='L'){
            //New coil pile
            new_life_pack(s);
        }       
    }
    
    // Working on 27/7/2013 2220h
    private void initialize(String s){
        System.out.println("Initializing");
        
        ln = ht = 10;
        coinpiles = new ArrayList<CoinPile>();
        lifepacks = new ArrayList<LifePack>();
        
        String[] temp = s.split(":");
        /*
         * temp[0] : I
         * temp[1] : Pn where n is your player no
         * temp[2] : Brick coordintates
         * temp[3] : Stone coordinates
         * temp[4] : Water coordinates
        */ 
        
        my_no = Integer.parseInt(temp[1].charAt(temp[1].length()-1)+"");
        
////        System.out.println("my_no: "+my_no);
//        
//        for(int i=0;i<temp.length;i++){
//            System.out.println(temp[i]);
//        }
        
        terrain = new int[ln][ht];
        
        //Set bricks in terrain
        {
            String[] temp_brick = temp[2].split(";");
            bricks = new Brick[temp_brick.length];
            for(int i=0;i<temp_brick.length;i++){
                bricks[i] = new Brick();
                int x,y;
                x = Integer.parseInt(temp_brick[i].charAt(0)+"");
                y = Integer.parseInt(temp_brick[i].charAt(2)+"");
                bricks[i].set_co(x, y);
                terrain[y][x] = 1; 
            }            
        }
        
        //Set stones in terrain
        {
            String[] temp_stone = temp[3].split(";");
            for(int i=0;i<temp_stone.length;i++){
                int x,y;
                x = Integer.parseInt(temp_stone[i].charAt(0)+"");
                y = Integer.parseInt(temp_stone[i].charAt(2)+"");                
                terrain[y][x] = 2; 
            }
        }
        
        //Set water in terrain
        {
            String[] temp_water = temp[4].split(";");
            for(int i=0;i<temp_water.length;i++){
                int x,y;
                x = Integer.parseInt(temp_water[i].charAt(0)+"");
                y = Integer.parseInt(temp_water[i].charAt(2)+"");
                terrain[y][x] = 3; 
            }
        }
        
//        System.out.println("  0 1 2 3 4 5 6 7 8 9");
//        for(int i=0;i<ht;i++){
//            System.out.print(i+" ");
//            for(int j=0;j<ln;j++){
//                switch (terrain[i][j]){
//                    case 0: System.out.print("N ");
//                    break;
//                    case 1: System.out.print("B ");
//                    break;
//                    case 2:System.out.print("S ");
//                    break;
//                    case 3:System.out.print("W ");
//                    break;                    
//                }
//            }
//            System.out.print("\n");
//        } 
        nxtmv = " ";
    } 
    
    // Working on 27/7/2013 2338h
    private void add_players(String s){
        System.out.println("Adding players");
        String[] temp = s.split(":");
        players = new Player[temp.length-1]; // n players + character S are divided by ':'
        
        for(int i=0;i<players.length;i++){
            players[i] = new Player();
//            P<num>;< player location  x>,< player location  y>;<Direction>
            players[i].player_no = Integer.parseInt(temp[i+1].charAt(1)+"");
            players[i].location[0] = Integer.parseInt(temp[i+1].charAt(3)+"");
            players[i].location[1] = Integer.parseInt(temp[i+1].charAt(5)+"");
            players[i].direction = Integer.parseInt(temp[i+1].charAt(7)+"");
            
//            System.out.println("Player: "+players[i].player_no+" x,y: "+players[i].location[0]+","+players[i].location[1]+" dir: "+players[i].direction);
        }
        
    }
    
    //Working on 28/7/2013 0021h
    private void global_update(String s){
        System.out.println("Global update recieved");
        String[] temp = s.split(":");  
        
        //P0;0,0;0;0;100;0;0
        //Assume player details come orderly everytime
        for(int i=0;i<players.length;i++){
            players[i].location[0] = Integer.parseInt(temp[i+1].charAt(3)+"");
            players[i].location[1] = Integer.parseInt(temp[i+1].charAt(5)+"");
            players[i].direction = Integer.parseInt(temp[i+1].charAt(7)+"");
            players[i].is_shot = Integer.parseInt(temp[i+1].charAt(9)+"");
            players[i].health = Integer.parseInt(temp[i+1].charAt(11)+"");
            players[i].coins = Integer.parseInt(temp[i+1].charAt(13)+"");
            players[i].points = Integer.parseInt(temp[i+1].charAt(15)+"");            
            terrain[players[i].location[1]][players[i].location[0]] = 4;
        }
        
        //6,2,0;5,4,0;7,1,0;9,3,0;1,7,0;0,2,0;6,8,0;8,6,0;0,3,0#";
        //Assume brick details come orderly everytime
//        System.out.println(temp[temp.length-1]);
        String[] temp2 = temp[temp.length-1].split(";");
        for(int i=0;i<temp2.length;i++){
            if(bricks[i].x==Integer.parseInt(temp2[i].charAt(0)+"")&&bricks[i].y==Integer.parseInt(temp2[i].charAt(2)+"")){
                bricks[i].dmg = Integer.parseInt(temp2[i].charAt(4)+"");    
            }
            else
                System.out.println("ERROR!!!");
        }
        
        update_coin_piles();
        update_life_packs();
        play();
    }
    
    //Working on 28/7/2013 0100h
    private void new_coin_pile(String s){
        System.out.println("New coin pile added");
        CoinPile cp = new CoinPile();
        String[] temp = s.split(":");
        //C:8,9:58511:1748#
        cp.set_co(Integer.parseInt(temp[1].charAt(0)+""),Integer.parseInt(temp[1].charAt(2)+""));
        cp.life_time = Integer.parseInt(temp[2]);
        cp.val = Integer.parseInt(temp[3].substring(0,temp[3].length()-1));
        
        terrain [cp.y][cp.x] = 5;
//        System.out.println(cp.life_time);
//        System.out.println(cp.val);
    }
    
    //Working on 28/7/2013 0100h
    private void update_coin_piles(){
        for(int i=0;i<coinpiles.size();i++){
            CoinPile cp = coinpiles.get(i);
            cp.life_time = cp.life_time-1000;
            if(cp.life_time<0){
                terrain[cp.y][cp.x] = 0;
                coinpiles.remove(i);
                i--;
            }
        }
    }
    
    //Working on 28/7/2013 0119h
    private void new_life_pack(String s){
        System.out.println("New life pack added");
        LifePack lp = new LifePack();
        String[] temp = s.split(":");
        //L:9,1:2914#
        lp.set_co(Integer.parseInt(temp[1].charAt(0)+""),Integer.parseInt(temp[1].charAt(2)+""));
        lp.life_time = Integer.parseInt(temp[2].substring(0,temp[2].length()-1));
        
        terrain [lp.y][lp.x] = 6;
        //System.out.println(lp.life_time);        
    }
    
    //Working on 28/7/2013 0119h
    private void update_life_packs(){
        for(int i=0;i<lifepacks.size();i++){
            LifePack lp = lifepacks.get(i);
            lp.life_time = lp.life_time-1000;
            if(lp.life_time<0){
                terrain[lp.y][lp.x] = 0;
                coinpiles.remove(i);
                i--;
            }
        }        
    }
    
    //Have to implement
    private void play(){       
        if(players[my_no].is_shot==1){
            under_attack();
            players[my_no].is_shot=0;
        }
        else{ //find the closest coinpile and go there
            seek_coin_pile();
        }
    }
    
    //Have to implement
    private void under_attack(){
        //Identify shooter
        
    }
    
    //Have to implement
    private void seek_coin_pile(){
        int[][] map = new int[ln][ht];
        PathCost nxt;
        
        // A map with visitable cordinates = 0
        for(int i=0;i<ln;i++){
            for(int j=0;j<ht;j++){
                if(terrain[i][j]==0|terrain[i][j]==5||terrain[i][j]==6)
                    map[i][j]=0;
                else
                    map[i][j]=1;
            }
        }
        nxt = seek(6,players[my_no].location[0],players[my_no].location[1],20,map);
        if(nxt.path.equals("NO#")){
            nxtmv = "SHOOT#";
        }
        else
            nxtmv = nxt.path;
    }
    
    private PathCost seek(int goal, int xx, int yy,int limit, int[][] map){
        if(limit <= 0){
            return new PathCost();
        }
        map[yy][xx] = 1;
        PathCost[] nxt = new PathCost[5];
        for(int i=0;i<5;i++){
            nxt[i]= new PathCost();
            nxt[i].cost=11;
        }
        
        if(terrain[yy][xx]==goal){
            nxt[0].cost = 0;
            nxt[0].path = "NO#";
        }
        else{
            if((yy-1)>=0 && map[yy-1][xx]==0){
                if(players[my_no].direction==1){
                    nxt[1].cost = seek(goal,xx,yy-1,limit-1,map).cost;
                    nxt[1].path = "UP#";
                }
                
                else{
                    nxt[1].cost = seek(goal,xx,yy-1,limit-2,map).cost;
                    nxt[1].path = "UP#";
                }
            }
            if((xx+1)<ln && map[yy][xx+1]==0){
                if(players[my_no].direction==2){
                    nxt[2].cost = seek(goal,xx+1,yy,limit-1,map).cost;
                    nxt[2].path = "RIGHT#";
                }
                else{
                    nxt[2].cost = seek(goal,xx+1,yy,limit-2,map).cost;
                    nxt[2].path = "RIGHT#";
                }
            }
            if((yy+1)<ht && map[yy+1][xx]==0){
                if(players[my_no].direction==3){
                    nxt[3].cost = seek(goal,xx,yy+1,limit-1,map).cost;
                    nxt[3].path = "DOWN#";
                }
                else{
                    nxt[3].cost = seek(goal,xx,yy+1,limit-2,map).cost;
                    nxt[3].path = "DOWN#";
                }
            }
            if((xx-1)>=0 && map[yy][xx-1]==0){
                if(players[my_no].direction==4){
                    nxt[4].cost = seek(goal,xx-1,yy,limit-1,map).cost;
                    nxt[4].path = "LEFT#";
                }
                else{
                    nxt[4].cost = seek(goal,xx-1,yy,limit-2,map).cost; 
                    nxt[4].path = "LEFT#";
                }
            }
            
        }
        
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                if(nxt[j].cost>nxt[j+1].cost){
                    PathCost temp = nxt[j];
                    nxt[j] = nxt[j+1];
                    nxt[j+1] = temp;
                }
            }
        }
        return nxt[0];
    }
}
