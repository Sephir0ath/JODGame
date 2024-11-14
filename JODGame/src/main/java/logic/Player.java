package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends GameObject {
    private ImageIcon playerWallTexture; // -> Solo por ahora para verificar muro de jugador
    private RayCaster rayCaster;
    private Rectangle hitBox;
    private double direction;
    private Wall playerWall; // -> Para deteccion enemigo a jugador
    private int velocity = 0;
    private Point pos;
    private int width;
    private int height;

    public Player(ImageIcon img) {
        super(img);
        width = img.getIconWidth();
        height = img.getIconHeight();
        hitBox = new Rectangle(20,20);
        rayCaster = new RayCaster(new Point(0,0));
        playerWall = new Wall(new Point(0,0), img.getIconHeight(), img.getIconWidth(), null);
    }


    public void addToDirection(double value){
        direction += value;
        normalizeDirection();
        rayCaster.updateRaysDirection(direction);
    }

    public void checkIntersectionWithHitBox(Wall wall) {
        if (hitBox.intersects(wall.getHitBox())) {
            handleCollisionWithWall(wall);
        }
    }
    public int getCollisionSide(Rectangle rectangle) {
        double hitBoxRight = hitBox.getX() + hitBox.getWidth();
        double hitBoxBottom = hitBox.getY() + hitBox.getHeight();
        double rectangleRight = rectangle.getX() + rectangle.getWidth();
        double rectangleBottom = rectangle.getY() + rectangle.getHeight();

        double topOverlap = hitBoxBottom - rectangle.getY();
        double bottomOverlap = rectangleBottom - hitBox.getY();
        double leftOverlap = hitBoxRight - rectangle.getX();
        double rightOverlap = rectangleRight - hitBox.getX();

        double minOverlap = Math.min(Math.min(topOverlap, bottomOverlap), Math.min(leftOverlap, rightOverlap));

        if (minOverlap == topOverlap) {
            return 0; // Top
        } else if (minOverlap == leftOverlap) {
            return 1; // Left
        } else if (minOverlap == rightOverlap) {
            return 2; // Right
        } else if (minOverlap == bottomOverlap) {
            return 3; // Bottom
        } else {
            return 4; // No collision
        }
    }

    public void handleCollisionWithWall(Wall wall) {
        switch (getCollisionSide(wall.getHitBox())) {
            case 0: // Top
                setPos(new Point(getPos().x, (int) (wall.getHitBox().getY() - hitBox.getHeight())));
                setVelocity(0);
                break;

            case 1: // Left
                setPos(new Point((int) (wall.getHitBox().getX() - hitBox.getWidth()), getPos().y));
                setVelocity(0);
                break;

            case 2: // Right
                setPos(new Point((int) (wall.getHitBox().getX() + wall.getHitBox().getWidth()), getPos().y));
                setVelocity(0);
                break;

            case 3: // Bottom
                setPos(new Point(getPos().x, (int) (wall.getHitBox().getY() + wall.getHitBox().getHeight())));
                setVelocity(0);
                break;
                
            default:
                break;
        }
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
        rayCaster.updateRaysPos(new Point(pos.x + width/2, pos.y + height/2));
        hitBox.setLocation((int) newX, (int) newY);

        playerWall.updatePosition(pos);
    }


    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
        playerWall.updatePosition(pos);
        rayCaster.updateRaysPos(new Point(pos.x+width/2, pos.y+height/2));
        hitBox.setLocation((int) pos.getX(), (int) pos.getY());
    }

    public Rectangle getHitBox(){
        return hitBox;
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
