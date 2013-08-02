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
    Graph g;
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
        //Take # out of the string
        s = s.substring(0, s.length()-1);
        // Default response is NO# 
        nxtmv="NO#";
        setParameter(s);
                       
        return nxtmv;
    }
    
    private void setParameter(String s){
        if(s.equals("OBSTACLE")){
            System.out.println("OBSTACLE HIT!!!");
            nxtmv = "NO#";
        }
        else if(s.equals("CELL_OCCUPIED")){
            System.out.println("CELL OCCUPIED. SHOOTING!!!");
            nxtmv = "SHOOT#";
        }
        else if(s.equals("DEAD")){
            System.out.println("FATAL CONDITION!!!");
                System.out.println("Player has been killed in action");
                System.out.println("Exiting game");
                System.exit(0);
        }
        else if(s.equals("TOO_QUICK")){
            //No problem, let the message be sent again
        }
        else if(s.equals("INVALID_CELL")){
            System.out.println("ERROR: INVALID CELL");
            System.out.println("Recoverable error");
            nxtmv = "NO#";
        }
        else if(s.equals("GAME_HAS_FINISHED")){
            System.out.println("FATAL ERROR!!!");
            System.out.println("GAME HAS FINISHED");
            System.out.println("Immidiate Exit");
            System.exit(3);
        }
        else if(s.equals("GAME_NOT_STARTED_YET")){
            System.out.println("GAME HAS NOT STARTED YET!!!");
            //Ok, let's waste some time until game starts
        }
        else if(s.equals("NOT_A_VALID_CONTESTANT")){
            System.out.println("FATAL ERROR!!!");
            System.out.println("NOT A VALID CONTESTANT");
            System.out.println("Immidiate Exit");
            System.exit(2);
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
        
        ln = ht = 20;
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
        System.out.println("Terrain length: "+terrain.length);
        
        //Set bricks in terrain
        try{
        {
            String[] temp_brick = temp[2].split(";");
            bricks = new Brick[temp_brick.length];
            for(int i=0;i<temp_brick.length;i++){
                bricks[i] = new Brick();
                int x,y;
                String[] temp_co = temp_brick[i].split(",");
                x = Integer.parseInt(temp_co[0]);
                y = Integer.parseInt(temp_co[1]); 
                bricks[i].set_co(x, y);
                terrain[x][y] = 1;                 
            }            
        }}catch(Exception e){
            System.out.println(e);
            System.out.println("At set bricks in terrain");
        }
        
        //Set stones in terrain
        try{
        {
            String[] temp_stone = temp[3].split(";");
            for(int i=0;i<temp_stone.length;i++){
                int x,y;
                String[] temp_co = temp_stone[i].split(",");
                x = Integer.parseInt(temp_co[0]);
                y = Integer.parseInt(temp_co[1]);                
                terrain[x][y] = 2; 
            }
        }}catch(Exception e){
            System.out.println(e);
            System.out.println("At set stones in terrain");
        }
        
        //Set water in terrain
        try{
        {
            String[] temp_water = temp[4].split(";");
            for(int i=0;i<temp_water.length;i++){
                int x,y;
                String[] temp_co = temp_water[i].split(",");
                x = Integer.parseInt(temp_co[0]);
                y = Integer.parseInt(temp_co[1]);
                terrain[x][y] = 3; 
            }
        }}catch(Exception e){
            System.out.println(e);
            System.out.println("At set water in terrain");
        }
        
        //Create graph for shortest path
        try{
        g = new Graph(ln,ht);
        g.initiate(terrain);
        }catch(Exception e){
            System.out.println("Error with graph initiation");
            nxtmv = "NO#";
        }
        
//        System.out.println("  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9");
//        for(int i=0;i<ht;i++){
//            System.out.print(i+" ");
//            for(int j=0;j<ln;j++){
//                switch (terrain[j][i]){
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
        nxtmv = "NO#";
    } 
    
    // Working on 27/7/2013 2338h
    private void add_players(String s){
        System.out.println("Adding players");
        String[] temp = s.split(":");
        players = new Player[temp.length-1]; // n players + character S are divided by ':'
        
        for(int i=0;i<players.length;i++){
            players[i] = new Player();
            String[] temp_plyr = temp[i+1].split(";");
            String[] temp_co = temp_plyr[1].split(",");
//            P<num>;< player location  x>,< player location  y>;<Direction>
            players[i].player_no = Integer.parseInt(temp_plyr[0].charAt(1)+"");
            players[i].location[0] = Integer.parseInt(temp_co[0]);
            players[i].location[1] = Integer.parseInt(temp_co[1]);
            players[i].direction = Integer.parseInt(temp_plyr[2]);
            
//            System.out.println("Player: "+players[i].player_no+" x,y: "+players[i].location[0]+","+players[i].location[1]+" dir: "+players[i].direction);
        }
        nxtmv = "NO#";
        
    }
    
    //Working on 28/7/2013 0021h
    private void global_update(String s){
        System.out.println("Global update recieved");
        String[] temp = s.split(":");  
        
        //P0;0,0;0;0;100;0;0
        //Assume player details come orderly everytime
        
        //Before initializing player info, terrain has to be cleared of all tanks
        for(int i=0;i<ln;i++){
            for(int j=0;j<ht;j++){
                if(terrain[i][j]==4){
                    terrain[i][j]=0;
                }
            }
        }
        for(int i=0;i<players.length;i++){
            String[] temp_plyr = temp[i+1].split(";");
            String[] temp_co = temp_plyr[1].split(",");
            players[i].location[0] = Integer.parseInt(temp_co[0]);
            players[i].location[1] = Integer.parseInt(temp_co[1]);
            players[i].direction = Integer.parseInt(temp_plyr[2]);
            players[i].shot = Integer.parseInt(temp_plyr[3]);
            players[i].set_health(Integer.parseInt(temp_plyr[4]));
            players[i].coins = Integer.parseInt(temp_plyr[5]);
            players[i].points = Integer.parseInt(temp_plyr[6]);            
            terrain[players[i].location[0]][players[i].location[1]] = 4;
        }
        //System.out.println("Players initialized");
        
        //6,2,0;5,4,0;7,1,0;9,3,0;1,7,0;0,2,0;6,8,0;8,6,0;0,3,0#";
        //Assume brick details come orderly everytime
//        System.out.println(temp[temp.length-1]);
        String[] temp2 = temp[temp.length-1].split(";");
        for(int i=0;i<temp2.length;i++){
            String[] temp_co = temp2[i].split(",");
            if(bricks[i].x==Integer.parseInt(temp_co[0])&&bricks[i].y==Integer.parseInt(temp_co[1])){
                bricks[i].dmg = Integer.parseInt(temp_co[2]);    
            }
            else{
                System.out.println("ERROR with global updates!!!");
                System.out.println(bricks[i].x+","+bricks[i].y+"!="+temp_co[0]+","+temp_co[1]);
            }
        }
        //System.out.println("Bricks initialized");
        try{
            check_cp_lp_acq();
//            System.out.println("cp_lp checked");
        }catch(Exception e){
            System.out.println("Exception at check acquired "+e);
        }
        try{
            update_coin_piles();
//            System.out.println("coin piles updated");
        }catch(Exception e){
            System.out.println("Exception at update cp "+e);
        }
        try{
            update_life_packs();
//            System.out.println("life packs updated");
        }catch(Exception e){
            System.out.println("Exception at uplate lp "+e);
        }
        try{
        play();
                System.out.println("played");
        }catch(Exception e){
            System.out.println("Exception at global_update.play "+e);
        }
    }
    
    //Working on 28/7/2013 0100h
    private void new_coin_pile(String s){
        System.out.println("New coin pile added");
        CoinPile cp = new CoinPile();
        String[] temp = s.split(":");
        String[] temp_co = temp[1].split(",");
        //C:8,9:58511:1748#
        //System.out.println("Coinpile: "+temp_co[0]+","+temp_co[1]);
        cp.set_co(Integer.parseInt(temp_co[0]),Integer.parseInt(temp_co[1]));
        cp.life_time = Integer.parseInt(temp[2]);
        cp.val = Integer.parseInt(temp[3]);
        coinpiles.add(cp);
        terrain [cp.x][cp.y] = 5;
//        System.out.println(cp.life_time);
//        System.out.println(cp.val);
    }
    
    //Working on 28/7/2013 0100h
    private void update_coin_piles(){
        for(int i=0;i<coinpiles.size();i++){
            CoinPile cp = coinpiles.get(i);
            cp.life_time = cp.life_time-1000;
            if(cp.life_time<0){
                terrain[cp.x][cp.y] = 0;
                coinpiles.remove(i);
                System.out.println("Coin Pile vanished");
                i--;
            }
        }
    }
    
    //Working on 28/7/2013 0119h
    private void new_life_pack(String s){
        System.out.println("New life pack added");
        LifePack lp = new LifePack();
        String[] temp = s.split(":");
        String[] temp_co = temp[1].split(",");
        //L:9,1:2914#
        lp.set_co(Integer.parseInt(temp_co[0]),Integer.parseInt(temp_co[1]));
        lp.life_time = Integer.parseInt(temp[2]);
        lifepacks.add(lp);
        terrain [lp.x][lp.y] = 6;
        //System.out.println(lp.life_time);        
    }
    
    //Working on 28/7/2013 0119h
    private void update_life_packs(){
        for(int i=0;i<lifepacks.size();i++){
            LifePack lp = lifepacks.get(i);
            lp.life_time = lp.life_time-1000;
            if(lp.life_time<0){
                terrain[lp.x][lp.y] = 0;
                lifepacks.remove(i);
                System.out.println("Life Pack vanished");
                i--;
            }
        }        
    }
    
    private void check_cp_lp_acq(){
        int xx,yy;
        CoinPile cp;
        LifePack lp;
        boolean changed = false;
        System.out.println(players.length);
        for(int i=0;i<players.length;i++){
            if(i==my_no) continue;
            else{
                xx = players[i].location[0];
                yy = players[i].location[1];
                for(int j=0;j<coinpiles.size();j++){
                    changed = false;
                    cp = coinpiles.get(j);
                    if(cp.x==xx && cp.y == yy){
                        coinpiles.remove(j); 
                        changed = true;
                    }                    
                    if(changed) j--;
                }
                
                for(int j=0;j<lifepacks.size();j++){
                    changed = false;
                    lp = lifepacks.get(j);
                    if(lp.x==xx && lp.y == yy){
                        lifepacks.remove(j); 
                        changed = true;
                    }
                    if(changed) j--;
                }
            }
        }
    }
    //Have to implement
    private void play(){ 
        if(false);
        //System.out.println("PLAY!!!");
        /*if(target_in_sight()){
            nxtmv = "SHOOT#";
            System.out.println(nxtmv);
        }*/
        
        
       /*else if(players[my_no].is_shot){
           //Find who shot and hunt
       }*/
        
        /*else if(players[my_no].health < 60 && lifepacks.size()>0){
            g.bfs(players[my_no].location[0], players[my_no].location[1]);
            int mv = g.find_shortest_path_lp(lifepacks);
            nxtmv = move_lp(mv);
            System.out.println(nxtmv);
        }*/        
        else if(coinpiles.size()>0){
            int mv=0;
            //System.out.println("Graph");
            try{
                g.bfs(players[my_no].location[0],players[my_no].location[1]);
                //System.out.println("g.bfs");
            }catch(Exception e){
                System.out.println("Exception in g.bfs "+e);
                nxtmv = "NO#";
            }
            try{
                mv = g.find_shortest_path_cp(coinpiles);
                //System.out.println("g.shortest path");
            }catch(Exception e){
                System.out.println("Exception in shortest path "+e);
                nxtmv = "NO#";
            }
            try{
                nxtmv = move_cp(mv);
                //System.out.println("nxtmv = mv");
            }catch(Exception e){
                System.out.println("Exception in move_cp "+e);
                nxtmv = "NO#";
            }
            System.out.println(nxtmv);
        }
        else if(lifepacks.size()>0){
            g.bfs(players[my_no].location[0], players[my_no].location[1]);
            int mv = g.find_shortest_path_lp(lifepacks);
            nxtmv = move_lp(mv);
            System.out.println(nxtmv);
        }
    }
    
    private boolean target_in_sight(){
        boolean in_sight = false;
        Player me = players[my_no];
        switch(me.direction){
            case 0:
                for(int i=me.location[1]-1;i>=0;i--){
                    if(terrain[me.location[0]][i]==1||terrain[me.location[0]][i]==2)
                        break;
                    if(terrain[me.location[0]][i]==4){
                        in_sight = true;
                        break;
                    }
                }
                break;
            case 1:
                for(int i=me.location[0]+1;i<ln;i++){
                    if(terrain[i][me.location[1]]==1||terrain[i][me.location[1]]==2)
                        break;
                    if(terrain[i][me.location[1]]==4){
                        in_sight = true;
                        break;
                    }
                }
                break;
            case 2:
                for(int i=me.location[1]+1;i<ht;i++){
                    if(terrain[me.location[0]][i]==1||terrain[me.location[0]][i]==2)
                        break;
                    if(terrain[me.location[0]][i]==4){
                        in_sight = true;
                        break;
                    }
                }
            case 3:
               for(int i=me.location[0]-1;i>=0;i--){
                    if(terrain[me.location[i]][1]==1||terrain[me.location[i]][1]==2)
                        break;
                    if(terrain[me.location[i]][1]==4){
                        in_sight = true;
                        break;
                    }
                } 
        }
        return in_sight;
    }
    
    //Working
    private String move_cp(int mv){
        Player me = players[my_no];
        int loc = me.location[1]*ln+me.location[0];
        int xx,yy;
        yy = mv/ln;
        xx = mv%ln;
        
        if(g.adj_mat[mv][loc]){
            if((mv/ln)<(loc/ln)){
                return "UP#";                
            }
            else if((mv/ln)>(loc/ln)){
                return "DOWN#";
            }
            else if(mv<loc){
                return "LEFT#";
            }
            else
                return "RIGHT#";
        }
        else if(mv == loc){
            //On top of the coinpile
            for(int i=0;i<coinpiles.size();i++){
                CoinPile cp = coinpiles.get(i);
                if(cp.x==xx && cp.y==yy){
                    coinpiles.remove(i);
                    System.out.println("Coin Pile Acquired");
                    break;
                }
            }
            return "NO#";
        }
        else{
            System.out.println("ERROR!!!");
            System.out.println("mv: "+mv+" loc: "+loc);
            System.out.println(me.location[0]+","+me.location[1]);
            System.out.println(mv-(mv/ln)+","+mv/ln);
            return "NO#";
        }
    }
    
    private String move_lp(int mv){
        Player me = players[my_no];
        int loc = me.location[1]*ln+me.location[0];
        int xx,yy;
        yy = mv/ln;
        xx = mv%ln;
        
        if(g.adj_mat[mv][loc]){
            if((mv/ln)<(loc/ln)){
                return "UP#";                
            }
            else if((mv/ln)>(loc/ln)){
                return "DOWN#";
            }
            else if(mv<loc){
                return "LEFT#";
            }
            else
                return "RIGHT#";
        }
        else if(mv == loc){
            //On top of the coinpile
            for(int i=0;i<coinpiles.size();i++){
                LifePack lp = lifepacks.get(i);
                if(lp.x==xx && lp.y==yy){
                    lifepacks.remove(i);
                    System.out.println("Life Pack Acquired");
                    break;
                }
            }
            return "NO#";
        }
        else{
            System.out.println("ERROR!!!");
            System.out.println("mv: "+mv+" loc: "+loc);
            System.out.println(me.location[0]+","+me.location[1]);
            System.out.println(mv-(mv/ln)+","+mv/ln);
            return "NO#";
        }
    }
}
