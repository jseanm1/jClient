package Algo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
 
public class ShortestPathBFS {

    public static boolean[][] edges = new boolean[120][120];
    //public static boolean seen[] = new boolean[10];
    public static int color[];
    public static int n = 0, num = 0;
    public static int parent[];
    public static int distance[];
    
   

    public void bfs(int s){
        
         Queue<Integer> q = new LinkedList<Integer>();
         color[s]=1;
         distance[s]=0;
         parent[s]=-1;
         q.add(s);
         
         while(!q.isEmpty()){
             int u = q.poll();
             
             for(int v=0;v<=n;++v){
                 if(color[v]==0 && edges[u][v]){
                     color[v]=1;
                     distance[v]=distance[u]+1;
                     parent[v]=u;
                     q.add(v);
                 }
             }
             color[u]=2;
             
         }
        
    }
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();// number of vertices
        int e = sc.nextInt();// number of edges
      //  seen = new boolean[n + 1];
        color = new int[n + 1];
        parent = new int[n + 1];
        distance = new int[n + 1];
        num = 1;

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        for (int i = 0; i < e; ++i) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            edges[u][v] = true;
            edges[v][u] = true;
        }

        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                if (edges[i][j]) {
                    System.out.print(" " + edges[i][j]);
                } else {
                    System.out.print(" ----");
                }
            }
            System.out.println("");
        }

        new ShortestPathBFS().bfs(1);
        System.out.println("");
        //System.out.println(Arrays.toString(seen));
        System.out.println(Arrays.toString(color));
        System.out.println(Arrays.toString(parent));
        System.out.println(Arrays.toString(distance));
    }
}
