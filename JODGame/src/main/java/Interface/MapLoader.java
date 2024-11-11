package Interface;

import logic.*;
import logic.Enemies.Enemy;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MapLoader extends JPanel {
    private int playerRowLocation, playerColLocation;
    private ArrayList<GameObject> gameObjects;
    private int tileHeight, tileWidth;
    private ArrayList<Wall> walls;
    private ArrayList<Enemy> enemies;
    private RayCaster rayCaster;
    private int mapColumnsSize;
    private int mapRowsSize;
    private int[][] mapMatrix;
    private Player player;
    public MapLoader(Player player, String mapFile) {
        super();
        this.player = player;
        gameObjects = new ArrayList<>();
        walls = new ArrayList<>();
        enemies = new ArrayList<>();
        mapRowsSize = 0;
        mapColumnsSize = 0;


        // Abrir el archivo del nivel y contar cuantas filas y columnas tiene para determinar el tamaño de cada objeto estatico
        try{
            BufferedReader reader1 = new BufferedReader(new FileReader(mapFile));
            String line;
            while ((line = reader1.readLine()) != null){
                mapColumnsSize = line.length();
                mapRowsSize++;

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tileWidth = Window.getInstance().getWidth() / mapColumnsSize;
        tileHeight = Window.getInstance().getHeight() / mapRowsSize;

        // Matriz del mapa (para movimiento de enemigos)
        mapMatrix = new int[mapRowsSize][mapColumnsSize];


        // Abrir nuevamente el archivo para poder guardar todos los objetos estaticos en un arreglo para poder pintarlos
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapFile));
            String line;
            int row = 0; // Filas
            while ((line = reader.readLine()) != null){
                for (int i = 0; i < mapColumnsSize; i++) { // Columnas
                    switch (line.charAt(i)){
                        case '0':
                            gameObjects.add(new NullSpace(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png")))));
                            mapMatrix[row][i] = 0;
                            break;

                        case '1':
                            gameObjects.add(new Wall(new Point(i*tileWidth, row*tileHeight),tileHeight, tileWidth, new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Wall.png")))));
                            walls.add(new Wall(new Point(i*tileWidth, row*tileHeight),tileHeight, tileWidth, new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Wall.png")))));
                            mapMatrix[row][i] = 1;
                            break;

                        case '2':
                            gameObjects.add(new NullSpace(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png")))));
                            playerRowLocation = row;
                            playerColLocation = i;
                            mapMatrix[row][i] = 2;
                            break;

                        case '3':
                            gameObjects.add(new NullSpace(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png")))));
                            ImageIcon enemyImg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("enemy.png")));
                            Enemy enemy = new Enemy(enemyImg, new Point(i * tileWidth + tileWidth/2, row * tileHeight + tileHeight/2));
                            enemies.add(enemy);
                            mapMatrix[row][i] = 3;
                            break;

                    }


                }
                row++;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        player.setPos(new Point(playerRowLocation*tileHeight, playerColLocation*tileWidth));
        System.out.println(player.getPos());

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int currentColumn = 0;
        int currentRow = 0;

        // Dibujar los objetos
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof NullSpace) {
                g.setColor(Color.BLACK);
                g.fillRect(currentColumn * tileWidth, currentRow * tileHeight, 800 / mapColumnsSize, 800 / mapRowsSize);
            } else if (gameObject instanceof Wall) {
                g.setColor(Color.WHITE);
                g.fillRect(currentColumn * tileWidth, currentRow * tileHeight, 800 / mapColumnsSize, 800 / mapRowsSize);
            }

            currentColumn++;
            if (currentColumn == mapColumnsSize) {
                currentColumn = 0;
                currentRow++;
            }


        }

        // Raycasting del jugador
        RayCaster playerRayCaster = player.getRaycaster();
        ArrayList<Point> endPoints = playerRayCaster.lookForObstacles(walls, player.getPlayerWall(), true);

        g.setColor(Color.GREEN);
        for (int i = 0; i < endPoints.size(); i++) {
            g.drawLine(playerRayCaster.getRaysArray().get(i).getPos().x, playerRayCaster.getRaysArray().get(i).getPos().y, endPoints.get(i).x, endPoints.get(i).y);
        }

        // Dibujar el jugador
        g.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player.png"))).getImage(), player.getPos().x, player.getPos().y,null);
        // Dibujar muro interno del jugador (por ahora para comprobar que funcione bien)
        ArrayList<Line> playerWallLines = player.getPlayerWall().getLines();
        for(Line line : playerWallLines) {
            g.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
        }

        // Renderizado de enemigos y raycasting
        for(Enemy enemy : enemies) {
            // enemy.setDetectingPlayer(false);
            g.setColor(Color.RED);
            g.drawImage(enemy.getTexture().getImage(), enemy.getPos().x, enemy.getPos().y, null);
            RayCaster enemyRayCaster = enemy.getRaycaster();
            ArrayList<Point> enemyEndPoints = enemyRayCaster.lookForObstacles(walls, player.getPlayerWall(), false);

            for(Point endPoint : enemyEndPoints) {
                g.drawLine(enemy.getPos().x, enemy.getPos().y, endPoint.x, endPoint.y);

            }

        }

        for(Enemy enemy : enemies) { // -> Solo para ver la ruta de movimiento del enemigo

            g.setColor(Color.BLUE);
            g.drawLine(enemy.getPos().x, enemy.getPos().y, enemy.getMovementPattern().getEndPoint().x, enemy.getMovementPattern().getEndPoint().y);

        }


    }

    public Point getPlayerFirstLocation(){
        return new Point(tileWidth*playerColLocation, tileHeight*playerRowLocation);
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int[][] getMapMatrix() {
        return mapMatrix;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

}
