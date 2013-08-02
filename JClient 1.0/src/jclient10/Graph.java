package jclient10;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    boolean[][] adj_mat;
    int[] visited;
    int[] parent;
    int[] distance;
    int ln;
    int ht;
    
    public Graph(int a,int b){
        adj_mat = new boolean[a*b][a*b];
        ln = a;
        ht = b;
        visited = new int[ln*ht];
        parent = new int[ln*ht];
        distance = new int[ln*ht];
    }
    
    public void initiate(int[][] arr){
        for(int j=0;j<ht;j++){
            for(int i=0;i<ln;i++){
                if(arr[i][j]==0){
                    if(i>0&&arr[i-1][j]==0)
                        adj_mat[j*ln+i][j*ln+i-1] = true;
                    if(i<ln-1&&arr[i+1][j]==0)
                        adj_mat[j*ln+i][j*ln+i+1] = true;
                    if(j>0&&arr[i][j-1]==0)
                        adj_mat[j*ln+i][(j-1)*ln+i] = true;
                    if(j<ht-1&&arr[i][j+1]==0)
                        adj_mat[j*ln+i][(j+1)*ln+i] = true;
                }                
            }
        }
    }
    
    public void bfs(int x,int y){
        // Reset arrays
        //System.out.println("bfs");
        for(int i=0;i<ln*ht;i++){
            visited[i] = 0;
            parent[i] = -1;
            distance[i] = 0;
        }
        Queue <Integer> q = new LinkedList <Integer>();
        int s = y*ln+x;
        visited[s] = 0;
        distance[s] = -1;
        
        q.add(s);
        
        while(!q.isEmpty()){
            int u = q.poll();
            
            for(int v=0;v<ln*ht;v++){
                if(visited[v]==0 && adj_mat[u][v]){
                    visited[v]=1;
                    distance[v]=distance[u]+1;
                    parent[v]=u;
                    q.add(v);
                }
            }
            visited[u]=2;
        }        
    }
    
    public int find_shortest_path_cp(ArrayList<CoinPile> cp){
        int index=0;
        int[] dst = new int[cp.size()];
        
        for(int i=0;i<cp.size();i++){
            CoinPile c = cp.get(i);
            dst[i] = distance[c.y*ln+c.x];
        }
        
        for(int i=0;i<cp.size();i++){
            if(dst[index]>dst[i])
                index=i;
        }
        //System.out.println(index+" ");
        int prnt = ((cp.get(index)).y)*ln+(cp.get(index)).x;
        while(distance[prnt]>0){
            prnt = parent[prnt];
        }
        return prnt;
    }
    
    public int find_shortest_path_lp(ArrayList<LifePack> lp){
        int index=0;
        int[] dst = new int[lp.size()];
        
        for(int i=0;i<lp.size();i++){
            LifePack l = lp.get(i);
            dst[i] = distance[l.y*ln+l.x];
        }
        
        for(int i=0;i<lp.size();i++){
            if(dst[index]>dst[i])
                index=i;
        }
        //System.out.println(index+" ");
        int prnt = ((lp.get(index)).y)*ln+(lp.get(index)).x;
        while(distance[prnt]>0){
            prnt = parent[prnt];
        }
        return prnt;
    }
}
