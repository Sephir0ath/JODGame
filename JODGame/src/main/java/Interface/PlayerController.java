package Interface;

import logic.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PlayerController implements KeyListener {
    private final Player player;
    private final Set<Integer> pressedKeys;
    public PlayerController(Player player) {
        this.player = player;
        this.pressedKeys = new HashSet<>();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && PrincipalPanel.getInstance().isActualPanelAMap()) {
            PrincipalPanel.getInstance().showPanel("MainMenu");

        }
        else{
            pressedKeys.add(e.getKeyCode());

            handlePlayerMovement();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());

        if (pressedKeys.isEmpty()) {
            player.setVelocity(0);
        }
        else{
            handlePlayerMovement();
        }
    }

    private void handlePlayerMovement(){
        int velocity = 0;
        int directionChange = 0;

        if (pressedKeys.contains(KeyEvent.VK_A)){
            directionChange -= 5;
        }

        if (pressedKeys.contains(KeyEvent.VK_D)){
            directionChange += 5;
        }

        if (pressedKeys.contains(KeyEvent.VK_W)){
            velocity = 200;
        }

        if (pressedKeys.contains(KeyEvent.VK_S)){
            velocity = -200;
        }




        player.addToDirection(directionChange);
        player.setVelocity(velocity);
    }
}
