package logic.Enemies;

import logic.Player;
import logic.RayCaster;
import logic.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PassiveEnemyMovement {
    private int[][] mapMatrix;
    ArrayList<Enemy> enemies;
    ArrayList<Wall> walls;
    Player player;
    private Rectangle patrolZone;
    private Random rand;
    private int tileWidth;
    private int tileHeight;


    public PassiveEnemyMovement() {
        this.mapMatrix = null;
        this.rand = new Random();
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

        MovementPattern movementPattern = assignRandomPattern(enemy.getPos());
        enemy.setMovementPattern(movementPattern);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public MovementPattern assignRandomPattern(Point startPoint) {
        int minDistance = 100; // distancia mínima de 100 píxeles
        int maxDistance = 250; // distancia máxima de 250 píxeles
        Point endPoint;

        do {
            int horizontalDist = 0;
            int verticalDist = 0;

            int direction = rand.nextInt(2); // 0 para horizontal, 1 para vertical

            if (direction == 0) {
                horizontalDist = rand.nextInt(maxDistance - minDistance + 1) + minDistance;
                if (rand.nextBoolean()) {
                    horizontalDist = -horizontalDist;
                }
            } else {
                verticalDist = rand.nextInt(maxDistance - minDistance + 1) + minDistance;
                if (rand.nextBoolean()) {
                    verticalDist = -verticalDist;
                }
            }

            endPoint = new Point(startPoint.x + horizontalDist, startPoint.y + verticalDist);

        } while (!isPointValid(endPoint)); // Verifica si el punto es válido, si no, sigue buscando

        return new LinearPattern(startPoint, endPoint, null);
    }

    public boolean isPointValid(Point point) {

        int cellX = point.x / tileHeight;
        int cellY = point.y / tileWidth;


        if (cellX < 0 || cellY < 0 || cellX >= mapMatrix[0].length || cellY >= mapMatrix.length) {
            return false;
        }

        return mapMatrix[cellX][cellY] == 0;
    }

    public void moveEnemy(Enemy enemy){
        MovementPattern movementPattern = enemy.getMovementPattern();
        Point endPoint = movementPattern.getEndPoint();

        rotateToPoint(enemy, endPoint);

        if(isFacingEndPoint(enemy, endPoint)) {
            travelToPoint(enemy, endPoint);
        }
    }

    public void travelToPoint(Enemy enemy, Point destinyPoint) {
        Point currentPos = enemy.getPos();

        enemy.setVelocity(100);

        double deltaX = destinyPoint.x - currentPos.x;
        double deltaY = destinyPoint.y - currentPos.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if(isObstacleNearby(enemy)) {
            enemy.setVelocity(0);
            MovementPattern movementPattern = assignRandomPattern(enemy.getPos());
            enemy.setMovementPattern(movementPattern);
        }

        if (distance < 10) { // -> suficientemente cerca de punto de destino
            enemy.setVelocity(0);
            MovementPattern movementPattern = assignRandomPattern(enemy.getPos());
            enemy.setMovementPattern(movementPattern);
        }


    }

    public void rotateToPoint(Enemy enemy, Point destinyPoint) {
        Point currentPos = enemy.getPos();

        // Calcular la dirección y distancia hacia el punto objetivo
        double deltaX = destinyPoint.x - currentPos.x;
        double deltaY = destinyPoint.y - currentPos.y;


        double objectiveDir = Math.toDegrees(Math.atan2(deltaY, deltaX));

        double currentDir = enemy.getDirection();
        double directionDifference = objectiveDir - currentDir;

        if (directionDifference > 180) {
            directionDifference -= 360;
        } else if (directionDifference < -180) {
            directionDifference += 360;
        }

        double rotationSpeed = 0.5;


        if (Math.abs(directionDifference) < rotationSpeed) {
            enemy.setDirection(objectiveDir);
        } else if (directionDifference > 0) {
            enemy.addToDirection(rotationSpeed); // Girar en sentido horario
        } else {
            enemy.addToDirection(-rotationSpeed); // Girar en sentido antihorario
        }

    }

    public boolean isFacingEndPoint(Enemy enemy, Point endPoint) {
        Point currentPos = enemy.getPos();

        // Calcular la dirección hacia el destino
        double deltaX = endPoint.x - currentPos.x;
        double deltaY = endPoint.y - currentPos.y;
        double targetDir = Math.toDegrees(Math.atan2(deltaY, deltaX));

        return Math.abs(targetDir - enemy.getDirection()) < 1.0; // -> compara direccion de destino con direccion de
                                                                 //    enemigo para saber si enemigo mira destino

    }

    public boolean isObstacleNearby(Enemy enemy) {
        RayCaster enemyRayCaster = enemy.getRaycaster();
        ArrayList<Point> enemyEndPoints = enemyRayCaster.lookForObstacles(walls, player.getPlayerWall(), false);

        for(Point enemyEndPoint : enemyEndPoints) {
            double deltaX = enemyEndPoint.x - enemy.getPos().x;
            double deltaY = enemyEndPoint.y - enemy.getPos().y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if(distance < 15){
                return true;
            }
        }

        return false;
    }


}
