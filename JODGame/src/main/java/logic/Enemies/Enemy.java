package logic.Enemies;

import logic.GameObject;
import logic.RayCaster;

import javax.swing.*;
import java.awt.*;

public class Enemy extends GameObject {
    // private Rectangle bounds; -> Por ahora no se para que usarlo
    private MovementPattern movementPattern;
    private boolean isDetectingPlayer;
    private RayCaster rayCaster;
    private double direction;
    private int velocity;
    private Point pos;
    private int width;
    private int height;

    public Enemy(ImageIcon img, Point pos){
        super(img);
        width = img.getIconWidth();
        height = img.getIconHeight();
        this.pos = pos;
        this.rayCaster = new RayCaster(this.pos);

    }

    public RayCaster getRaycaster(){
        return rayCaster;
    }

    public Point getPos(){
        return this.pos;
    }

    public void updateEnemyPosition(double timeStep){
        double newX = pos.getX() + velocity*timeStep * Math.cos(Math.toRadians(direction));
        double newY = pos.getY() + velocity*timeStep * Math.sin(Math.toRadians(direction));

        this.pos.setLocation(newX, newY);
        rayCaster.updateRaysPos(new Point(pos.x+width/2, pos.y+height/2));

    }

    private void normalizeDirection() {
        this.direction = (this.direction % 360 + 360) % 360;
    }

    public void setVelocity(int velocity){
        this.velocity = velocity;
    }

    public int getVelocity(){
        return this.velocity;
    }

    public void addToDirection(double value){
        this.direction += value;
        normalizeDirection();
        rayCaster.updateRaysDirection(direction);
    }

    public void setPos(Point pos) {
        this.pos = pos;
        rayCaster.updateRaysPos(new Point(pos.x+width/2, pos.y+height/2));
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setMovementPattern(MovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }

    public MovementPattern getMovementPattern() {
        return movementPattern;
    }

    public void setDetectingPlayer(boolean detectingPlayer) {
        this.isDetectingPlayer = detectingPlayer;
    }
    public boolean isDetectingPlayer() {
        return isDetectingPlayer;
    }
}
