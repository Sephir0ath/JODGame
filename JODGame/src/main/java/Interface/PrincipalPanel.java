package Interface;

import Interface.menu.CreditsJPanel;
import Interface.menu.MainMenuJPanel;
import Interface.menu.PlayLevelsJPanel;
import Interface.menu.SettingsJPanel;
import logic.Enemies.ActiveEnemyMovement;
import logic.Enemies.Enemy;
import logic.Enemies.PassiveEnemyMovement;
import logic.Player;
import logic.Wall;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrincipalPanel extends JPanel {
    private PassiveEnemyMovement passiveEnemyMovement;
    private ActiveEnemyMovement activeEnemyMovement;
    private PlayerController playerController;
    private static boolean isActualPanelAMap;
    private ArrayList<MapLoader> premadeMaps;
    private EnemyController enemyController;
    private static PrincipalPanel instance;
    private ArrayList<Wall> wallArrayList;
    private CardLayout cardLayout;
    private MapLoader mapLoader;
    private final Player player;
    private JPanel actualPanel;

    public PrincipalPanel() {
        super();
        wallArrayList = new ArrayList<>();
        this.player = new Player(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png"))));
        premadeMaps = new ArrayList<>();
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        isActualPanelAMap = false;
        instance = this;


        // -------- Menú --------------
        MainMenuJPanel mainMenuJPanel = new MainMenuJPanel();
        PlayLevelsJPanel playLevelsJPanel = new PlayLevelsJPanel();
        CreditsJPanel creditsJPanel = new CreditsJPanel();
        SettingsJPanel settingsJPanel = new SettingsJPanel();

        add(mainMenuJPanel, "MainMenu"); // Menú principal
        add(playLevelsJPanel, "PlayLevels"); // Menú de los niveles prehechos
        add(settingsJPanel, "Settings");
        add(creditsJPanel, "Credits");

        // -------- Levels ------------
        for (int i = 0; i < 7; i++) {
            premadeMaps.add(new MapLoader(player, "src/main/resources/map" + i + ".txt"));
            add(premadeMaps.get(i), "map" + i);
        }


        playerController = new PlayerController(this.player);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(playerController);
        enemyController = new EnemyController();
        addKeyListener(enemyController);
        passiveEnemyMovement = new PassiveEnemyMovement();
        activeEnemyMovement = new ActiveEnemyMovement();

        //Player movement scheduler
        // Esto repinta los niveles y actualiza la posición del jugador

        // QUIZA DEJAR EN UN SOLO HILO EVITA ERRORES DE CONCURRENCIA
        ScheduledExecutorService updateScheduler = new ScheduledThreadPoolExecutor(1);
        updateScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isActualPanelAMap) {
                    player.updatePlayerPosition(0.016);

                    for (int i = 0; i < wallArrayList.size(); i++) {
                        player.checkIntersectionWithHitBox(wallArrayList.get(i));
                    }
                    mapLoader.repaint();
                }

            }
        }, 0, 10, TimeUnit.MILLISECONDS);


        // ES POSIBLE QUE USAR ESTE OTRO HILO PARA LOS ENEMIGOS CAUSE EXCEPCIONES DE CONCURRENCIA
        ScheduledExecutorService enemiesScheduler = new ScheduledThreadPoolExecutor(8);
        addKeyListener(enemyController);
        enemiesScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isActualPanelAMap) {
                    enemyController.updateAllEnemiesPositions(0.016);
                    for(Enemy enemy : passiveEnemyMovement.getEnemies()) {
//                    if(!enemy.isDetectingPlayer()) {
//                        passiveEnemyMovement.moveEnemy(enemy);
//                    }
                        passiveEnemyMovement.moveEnemy(enemy);

                    }
                    mapLoader.repaint();
                }
            }
        }, 0, 10, TimeUnit.MILLISECONDS);

    }

    public void addPanel(JPanel panel, String name) {
        this.add(panel, name);
    }

    public void showPanel(String panelName){ // -> Acá mapLoader tiene un valor asignado, luego se puede obtener el array de enemigos
        cardLayout.show(this, panelName);
        if (panelName.contains("map")){
            isActualPanelAMap = true;
            mapLoader = premadeMaps.get(Character.getNumericValue(panelName.charAt(panelName.length() - 1))); // Obtener el index del ultimo digito del String de panelName
            player.setPos(mapLoader.getPlayerFirstLocation());
            wallArrayList = mapLoader.getWalls();

            enemyController.setEnemiesArray(mapLoader.getEnemies()); // -> Para movimiento con teclado (para test)

            for(Enemy enemy : mapLoader.getEnemies()) {
                passiveEnemyMovement.setupEnemyMovement(enemy, mapLoader.getEnemies(), mapLoader.getWalls(),
                                                 mapLoader.getPlayer(), mapLoader.getMapMatrix(),
                                                 mapLoader.getTileWidth(), mapLoader.getTileHeight()); // -> Para movimiento inteligente de enemigos

                activeEnemyMovement.setupEnemyMovement(enemy, mapLoader.getEnemies(), mapLoader.getWalls(),
                                                 mapLoader.getPlayer(), mapLoader.getMapMatrix(),
                                                 mapLoader.getTileWidth(), mapLoader.getTileHeight());

            }


        }

        else{
            isActualPanelAMap = false;
        }

    }

    public static PrincipalPanel getInstance(){
        if(instance == null){
            instance = new PrincipalPanel();
        }
        return instance;
    }

    public Player getPlayer(){
        return this.player;
    }

    public boolean isActualPanelAMap(){
        return isActualPanelAMap;
    }

}
