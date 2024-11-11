package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player extends GameObject {
    private RayCaster rayCaster;
    private double direction;
    private int velocity = 0;
    private Point pos;


    public Player(ImageIcon img) {
        super(img);
        rayCaster = new RayCaster(new Point(0,0));
    }


    public void addToDirection(double value){
        direction += value;
        normalizeDirection();
        rayCaster.updateRaysDirection(direction);
    }

    private void normalizeDirection() {
        this.direction = (this.direction % 360 + 360) % 360;
    }

    public void setVelocity(int velocity){
        this.velocity = velocity;
    }

    public void updatePlayerPosition(double timeStep){
        double newX = pos.getX() + velocity*timeStep * Math.cos(Math.toRadians(direction));
        double newY = pos.getY() + velocity*timeStep * Math.sin(Math.toRadians(direction));

        pos.setLocation(newX, newY);
        rayCaster.updateRaysPos(pos);

    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
        rayCaster.updateRaysPos(pos);
    }

    public RayCaster getRaycaster() {
        return rayCaster;
    }

    public double getDirection() {
        return direction;
    }

}
