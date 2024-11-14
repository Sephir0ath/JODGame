package Interface;

import logic.Enemies.Enemy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EnemyController implements KeyListener {
    private final Set<Integer> pressedKeys;
    private ArrayList<Enemy> enemies;

    public EnemyController() {
        this.pressedKeys = new HashSet<>();
        this.enemies = new ArrayList<>();
    }

    public void setEnemiesArray(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());

        handleEnemiesMovement();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());

        if (pressedKeys.isEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.setVelocity(0);
            }
        } else {
            handleEnemiesMovement();
        }
    }

    public void updateAllEnemiesPositions(double timeStep) {
        for (Enemy enemy : enemies) {
            enemy.updateEnemyPosition(timeStep);
        }
    }

    public void handleEnemiesMovement() {

        for(Enemy enemy : enemies) {
            int velocity = 0;
            int directionChange = 0;

            if (pressedKeys.contains(KeyEvent.VK_LEFT)){
                directionChange -= 5;
            }

            if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
                directionChange += 5;
            }

            if (pressedKeys.contains(KeyEvent.VK_UP)){
                velocity = 200;
            }

            if (pressedKeys.contains(KeyEvent.VK_DOWN)){
                velocity = -200;
            }




            enemy.addToDirection(directionChange);
            enemy.setVelocity(velocity);

        }
    }
}
