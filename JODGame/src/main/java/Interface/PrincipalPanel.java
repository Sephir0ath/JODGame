package Interface;

import Interface.menu.MainMenu;
import Interface.menu.PlayLevelsJPanel;
import logic.MapLoader;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrincipalPanel extends JPanel {
    private PlayerController playerController;
    private ArrayList<MapLoader> premadeMaps;
    private static PrincipalPanel instance;
    private static boolean isActualPanelAMap;
    private CardLayout cardLayout;
    private final Player player;
    private JPanel actualPanel;
    private MapLoader mapLoader;

    public PrincipalPanel() {
        super();
        this.player = new Player(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png"))));
        premadeMaps = new ArrayList<>();
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        isActualPanelAMap = false;
        instance = this;


        // -------- Menú --------------
        MainMenu mainMenu = new MainMenu();
        PlayLevelsJPanel playLevelsJPanel = new PlayLevelsJPanel();

        add(mainMenu, "MainMenu"); // Menú principal
        add(playLevelsJPanel, "PlayLevels"); // Menú de los niveles prehechos

        // -------- Levels ------------
        for (int i = 0; i < 6; i++) {
            premadeMaps.add(new MapLoader(player, "src/main/resources/map" + i + ".txt"));
            add(premadeMaps.get(i), "map" + i);
        }


        playerController = new PlayerController(this.player);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(playerController);

        //Player movement scheduler
        // Esto repinta los niveles y actualiza la posición del jugador
        ScheduledExecutorService playerScheduler = new ScheduledThreadPoolExecutor(1);
        playerScheduler.scheduleAtFixedRate(new Runnable() {


            @Override
            public void run() {
                player.updatePlayerPosition(0.016);
                if (isActualPanelAMap) {
                    mapLoader.repaint();
                }

            }
        }, 0, 10, TimeUnit.MILLISECONDS);




    }

    public void addPanel(JPanel panel, String name) {
        this.add(panel, name);
    }

    public void showPanel(String panelName){
        cardLayout.show(this, panelName);
        if (panelName.contains("map")){
            isActualPanelAMap = true;
            mapLoader = premadeMaps.get(Character.getNumericValue(panelName.charAt(panelName.length() - 1))); // Obtener el index del ultimo digito del String de panelName
            player.setPos(mapLoader.getPlayerFirstLocation());
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
