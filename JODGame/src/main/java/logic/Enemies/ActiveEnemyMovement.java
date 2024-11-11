package logic.Enemies;

import logic.Player;
import logic.Wall;

import java.awt.*;
import java.util.ArrayList;

public class ActiveEnemyMovement {
    private int[][] mapMatrix;
    private int[][] costMatrix;
    ArrayList<Enemy> enemies;
    ArrayList<Wall> walls;
    Player player;
    private int tileWidth;
    private int tileHeight;

    public ActiveEnemyMovement() {
        this.mapMatrix = null;
        this.costMatrix = null;
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
    }

    public void setupEnemyMovement(Enemy enemy, ArrayList<Enemy> enemies, ArrayList<Wall> walls, Player player, int[][] mapMatrix, int tileWidth, int tileHeight) {

        this.enemies = enemies;
        this.mapMatrix = mapMatrix;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.walls = walls;
        this.player = player;



    }

    public void initCostMatrix() {
        int rows = mapMatrix.length;
        int cols = mapMatrix[0].length;
        this.costMatrix = new int[rows][cols];

        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {

                if(mapMatrix[i][j] == 1) {
                    this.costMatrix[i][j] = 100000; // -> Coste muy grande
                } else {
                    this.costMatrix[i][j] = 1; // -> Celda transitable
                }
            }
        }
    }

    public int getCost(int row, int col) {
        return this.costMatrix[row][col];
    }

    public void moveEnemy(Enemy enemy, Player player) {



    }

    public void showMapMatrix() {
        for(int i = 0;i < mapMatrix.length;i++){
            for(int j = 0;j < mapMatrix.length;j++){
                System.out.println(mapMatrix[i][j]);
            }

        }
    }

    public double heuristic(Point enemyPos, Point playerPos) {
        double deltaX = playerPos.x - enemyPos.x;
        double deltaY = playerPos.y - enemyPos.y;

        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    public Point getMatrixIndex(Point position) {
        int row = position.y / tileHeight;
        int col = position.x / tileWidth;

        // NECESITO VER FORMA DE REDONDEAR PUNTOS CARTESIANOS A CELDAS DE LA MATRIZ PARA VALORES NO EXACTOS

        return new Point(row - 1, col - 1);
    }

}
