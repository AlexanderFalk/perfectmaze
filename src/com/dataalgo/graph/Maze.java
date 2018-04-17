package com.dataalgo.graph;

import java.util.Random;

public class Maze {


    private int n;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;

    private Random random = new Random();

    public Maze(int n) {
        this.n = n;
        init();
        generate();
    }

    public void generateMaze(int x, int y) {
        visited[x][y] = true;

        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            int rand = random.nextInt(4);
            //System.out.println(rand);
            if (rand == 0 && !visited[x][y+1]){
                north[x][y] = false;
                south[x][y+1] = false;
                generateMaze(x, y + 1);
                break;
            }
            else if (rand == 1 && !visited[x+1][y]) {
                east[x][y] = false;
                west[x+1][y] = false;
                generateMaze(x+1, y);
                break;
            }
            else if (rand == 2 && !visited[x][y-1]) {
                south[x][y] = false;
                north[x][y-1] = false;
                generateMaze(x, y-1);
                break;
            }
            else if (rand == 3 && !visited[x-1][y]) {
                west[x][y] = false;
                east[x-1][y] = false;
                generateMaze(x-1, y);
                break;
            }
        }
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = true;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }


        // initialze all walls as present
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }
    // generate the maze starting from lower left
    private void generate() {
        generateMaze(1, 1);
    }

    void depthfirst(int x, int y) {
        if (x == 0 || y == 0 || x == n+1 || y == n+1) return;

        if (done || visited[x][y]) return;
        visited[x][y] = true;
        System.out.println("X="+x+" : " + "Y="+y);
        // reached middle
        if (x == n/2 && y == n/2) done = true;

        if (!north[x][y]) depthfirst(x, y + 1);
        if (!east[x][y])  depthfirst(x + 1, y);
        if (!south[x][y]) depthfirst(x, y - 1);
        if (!west[x][y])  depthfirst(x - 1, y);

        if (done) return;
    }

    // solve the maze starting from the start state
    void solve() {

        for (int x = 1; x <= n; x++)
            for (int y = 1; y <= n; y++)
                visited[x][y] = false;

        done = false;
        depthfirst(1, 1);
    }

    public static void main(String[] args) {
        Maze maze = new Maze(51);

        maze.solve();
    }
}
