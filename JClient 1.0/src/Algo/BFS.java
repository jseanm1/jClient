/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {

    public static boolean[][] edges = new boolean[120][120];
    public static boolean seen[] = new boolean[10];
    public static int color[];
    public static int n = 0, num = 0;
    public static int dfsNum[];
    public static int parent[];

    // s = source vertex
    public void bfs(int s) {

        Queue<Integer> q = new LinkedList<Integer>();

        seen[s] = true;
        parent[s] = -1;
        q.add(s);

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v = 0; v <= n; ++v) {
                if (!seen[v] && edges[u][v]) {
                    seen[v] = true;
                    q.add(v);
                    parent[v] = u;
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();// number of vertices
        int e = sc.nextInt();// number of edges
        seen = new boolean[n + 1];
        color = new int[n + 1];
        dfsNum = new int[n + 1];
        parent = new int[n + 1];
        num = 1;


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

        new BFS().bfs(1);
        System.out.println("");
        System.out.println(Arrays.toString(seen));
        System.out.println(Arrays.toString(color));
        System.out.println(Arrays.toString(dfsNum));
        System.out.println(Arrays.toString(parent));
    }
}
