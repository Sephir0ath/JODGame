package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player extends GameObject {
    private Point pos;
    private int velocity = 0;
    private final int MAX_DISTANCE_VIEW = 400;
    private double direction;
    private RayCaster rayCaster;


    public Player(ImageIcon img) {
        super(img);
        rayCaster = new RayCaster(new Point(0,0));
    }


    public void addToDirection(double value){
        direction += value;
        normalizeDirection();
        System.out.println(direction);
        rayCaster.updateRaysDirection(direction);
    }

    private void normalizeDirection() {
        // Ensure direction is within 0-360 by wrapping it around
        this.direction = (this.direction % 360 + 360) % 360;
    }

    public void setVelocity(int velocity){
        this.velocity = velocity;
    }

    public void updatePlayerPosition(double timeStep){
        double newX = pos.getX() + velocity*timeStep * Math.cos(direction * Math.PI / 180);
        double newY = pos.getY() + velocity*timeStep * Math.sin(direction * Math.PI / 180);

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

}
