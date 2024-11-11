package Interface;

import Interface.menu.MainMenu;
import Interface.menu.PlayLevelsJPanel;
import logic.MapLoader;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.security.Principal;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrincipalPanel extends JPanel {
    private final Player player;
    private CardLayout cardLayout;
    private static PrincipalPanel instance;

    public PrincipalPanel() {
        super();
        instance = this;
        this.player = new Player(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NullSpace.png"))));
        cardLayout = new CardLayout();
        MapLoader mapLoader = new MapLoader(player, "src/main/resources/map2.txt");
        this.setLayout(cardLayout);



        // -------- Menú --------------
        MainMenu mainMenu = new MainMenu();
        PlayLevelsJPanel playLevelsJPanel = new PlayLevelsJPanel();

        this.add(mapLoader, "mapLoader");
        add(mainMenu, "MainMenu");
        add(playLevelsJPanel, "PlayLevels");

        // -------- Levels ------------
/*        for(int i = 0; i < 5; i++){
            add(new MapLoader(player, "src/main/resources/map"+i+".txt"), "map"+i);
        }
*/

        PlayerController playerController = new PlayerController(this.player);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(playerController);

        //Player movement scheduler
        ScheduledExecutorService playerScheduler = new ScheduledThreadPoolExecutor(1);
        playerScheduler.scheduleAtFixedRate(new Runnable() {


            @Override
            public void run() {
                player.updatePlayerPosition(0.016);
                mapLoader.repaint();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);




    }

    public void addPanel(JPanel panel, String name) {
        this.add(panel, name);
    }

    public void showPanel(String panelName){
        cardLayout.show(this, panelName);
        System.out.println("lol");
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
}
