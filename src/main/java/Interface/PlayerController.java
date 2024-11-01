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
        pressedKeys.add(e.getKeyCode());

        handlePlayerMovement();
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
        player.setVelocity(0);

        if (pressedKeys.contains(KeyEvent.VK_W)){
            player.setVelocity(200);
        }

        else if (pressedKeys.contains(KeyEvent.VK_S)){
            player.setVelocity(-200);
        }
        else{
            player.setVelocity(0);
        }

        if (pressedKeys.contains(KeyEvent.VK_A)){
            player.addToDirection(-5);
        }

        if (pressedKeys.contains(KeyEvent.VK_D)){
            player.addToDirection(5);
        }

    }
}
