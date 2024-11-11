package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends GameObject {
    private RayCaster rayCaster;
    private double direction;
    private int velocity = 0;
    private Point pos;
    private Wall playerWall; // -> Para deteccion enemigo a jugador
    private ImageIcon playerWallTexture; // -> Solo por ahora para verificar muro de jugador

    public Player(ImageIcon img) {
        super(img);
        rayCaster = new RayCaster(new Point(0,0));
        playerWall = new Wall(new Point(0,0), img.getIconHeight(), img.getIconWidth(), null);
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

        playerWall.updatePosition(pos);
    }


    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
        playerWall.updatePosition(pos);
        rayCaster.updateRaysPos(pos);
    }


    public RayCaster getRaycaster() {
        return rayCaster;
    }


    public double getDirection() {
        return direction;
    }

    public ImageIcon getPlayerWallTexture() {
        return playerWallTexture;
    }

    public Point getPlayerWallPosition() {
        return pos;
    }

    public Wall getPlayerWall() {
        return playerWall;
    }
}
