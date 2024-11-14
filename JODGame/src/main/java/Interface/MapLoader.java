package Interface;

import logic.*;
import logic.Enemies.Enemy;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MapLoader extends JPanel {
    private int playerRowLocation, playerColLocation;
    private ArrayList<GameObject> gameObjects;
    private int tileHeight, tileWidth;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;
    private RayCaster rayCaster;
    private int mapColumnsSize;
    private int[][] mapMatrix;
    private int mapRowsSize;
    private Player player;
    public MapLoader(Player player, String mapFile) {
        super();
        this.player = player;
        this.setBackground(Color.gray);
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
                            gameObjects.add(new Tile(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Tile3.png")))));
                            mapMatrix[row][i] = 0;
                            break;

                        case '1':
                            gameObjects.add(new Wall(new Point(i*tileWidth, row*tileHeight),tileHeight, tileWidth, new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Wall1.png")))));
                            walls.add(new Wall(new Point(i*tileWidth, row*tileHeight),tileHeight, tileWidth, new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Wall1.png")))));
                            mapMatrix[row][i] = 1;
                            break;

                        case '2':
                            gameObjects.add(new Tile(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Tile3.png")))));
                            playerRowLocation = row;
                            playerColLocation = i;
                            mapMatrix[row][i] = 2;
                            break;

                        case '3':
                            gameObjects.add(new Tile(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Tile3.png")))));
                            ImageIcon enemyImg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("enemy2.png")));
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

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int currentColumn = 0;
        int currentRow = 0;

        // Dibujar los objetos ACA AGREGAR IMAGENES Y AÑADIR UN METODO PARA REDIMENSIONAR IMAGENES A TAMAÑO DE CELDA
        for (GameObject gameObject : gameObjects) {
            Image textureImage = gameObject.getTexture().getImage();
            if (gameObject instanceof Tile) {
                g.drawImage(textureImage, currentColumn * tileWidth, currentRow * tileHeight,tileWidth, tileHeight, null);
            } else if (gameObject instanceof Wall) {
                g.drawImage(textureImage, currentColumn * tileWidth, currentRow * tileHeight,tileWidth, tileHeight, null);


            }

            currentColumn++;
            if (currentColumn == mapColumnsSize) {
                currentColumn = 0;
                currentRow++;
            }


        }

        // Raycasting del jugador
        RayCaster playerRayCaster = player.getRaycaster();
        ArrayList<Point> endPoints = playerRayCaster.lookForObstacles(walls, player.getPlayerWall(), true, null);

        g.setColor(Color.GREEN);
        for (int i = 0; i < endPoints.size(); i++) {
            g.drawLine(playerRayCaster.getRaysArray().get(i).getPos().x, playerRayCaster.getRaysArray().get(i).getPos().y, endPoints.get(i).x, endPoints.get(i).y);
        }

        // Dibujar el jugador

        //d.drawImage(ImageIO.read(new File("src/main/resources/player.png")), player.getPos().x, player.getPos().y,null);
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
            ArrayList<Point> enemyEndPoints = enemyRayCaster.lookForObstacles(walls, player.getPlayerWall(), false, enemy);

            for (int i = 0; i < endPoints.size(); i++) {
                g.drawLine(enemyRayCaster.getRaysArray().get(i).getPos().x, enemyRayCaster.getRaysArray().get(i).getPos().y, enemyEndPoints.get(i).x, enemyEndPoints.get(i).y);
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
