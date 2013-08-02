package jclient10;

public class AlgoTest {
     /*--------------------------------------------------------------------------
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
                if(terrain[i][j]==0||terrain[i][j]==5||terrain[i][j]==6)
                    map[i][j]=0;
                else
                    map[i][j]=1;
            }
        }
        System.out.println(players[my_no].location[0]+","+players[my_no].location[1]);
        nxt = seek(5,players[my_no].location[0],players[my_no].location[1],map);
        nxtmv = nxt.path;
        if(nxtmv .equals(""))
            nxtmv = idle();
        System.out.println(nxtmv);
    }
    
    private PathCost seek(int goal, int xx, int yy, int[][] map){
        map[xx][yy] = 1;
        PathCost[] nxt = new PathCost[5];
        for(int i=0;i<5;i++){
            nxt[i]= new PathCost();            
        }
        
        if(terrain[xx][yy]==goal){
            nxt[0].path = "SHOOT#";
            nxt[0].cost = 0;
        }
        else{
            if((yy-1)>=0 && map[xx][yy-1]==0){
                if(players[my_no].direction==1){
                    nxt[1].cost = 1 + seek(goal,xx,yy-1,map).cost;
                    nxt[1].path = "UP#";
                }                
                else{
                    nxt[1].cost = 2 + seek(goal,xx,yy-1,map).cost;
                    nxt[1].path = "UP#";
                }
            }
            if((xx+1)<ln && map[xx+1][yy]==0){
                if(players[my_no].direction==2){
                    nxt[2].cost = 1 + seek(goal,xx+1,yy,map).cost;
                    nxt[2].path = "RIGHT#";
                }
                else{
                    nxt[2].cost = 2 + seek(goal,xx+1,yy,map).cost;
                    nxt[2].path = "RIGHT#";
                }
            }
            if((yy+1)<ht && map[xx][yy+1]==0){
                if(players[my_no].direction==3){
                    nxt[3].cost = 1 + seek(goal,xx,yy+1,map).cost;
                    nxt[3].path = "DOWN#";
                }
                else{
                    nxt[3].cost = 2 + seek(goal,xx,yy+1,map).cost;
                    nxt[3].path = "DOWN#";
                }
            }
            if((xx-1)>=0 && map[xx-1][yy]==0){
                if(players[my_no].direction==4){
                    nxt[4].cost = 1 + seek(goal,xx-1,yy,map).cost;
                    nxt[4].path = "LEFT#";
                }
                else{
                    nxt[4].cost = 2 + seek(goal,xx-1,yy,map).cost; 
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
    
    private String idle(){
        int xx,yy;
        xx = players[my_no].location[0];
        yy = players[my_no].location[1];
        
        if(xx+1<ln&&terrain[xx+1][yy]!=1&&terrain[xx+1][yy]!=2&&terrain[xx+1][yy]!=3&&terrain[xx+1][yy]!=4)
            return "RIGHT#";
        else if(yy+1<ht&&terrain[xx][yy+1]!=1&&terrain[xx][yy+1]!=2&&terrain[xx][yy+1]!=3&&terrain[xx][yy+1]!=4)
            return "DOWN#";
        else if(xx-1>-1&&terrain[xx-1][yy]!=1&&terrain[xx-1][yy]!=2&&terrain[xx-1][yy]!=3&&terrain[xx-1][yy]!=4)
            return "RIGHT#";
        else 
            return "UP#";
    }
     -------------------------------------------------------------------------*/
    
    /*
     * -------------------------------------------------------------------------
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
     * -----------------------------------------------------------------------*/
}
