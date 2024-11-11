package logic;

import javax.swing.*;
import java.awt.*;

public class Enemy extends GameObject{
    private RayCaster rayCaster;
    private Point pos;
    private int direction;
    // private Rectangle bounds; -> Por ahora no se para que usarlo

    public Enemy(ImageIcon img, Point pos){
        super(img);
        this.pos = pos;
        this.rayCaster = new RayCaster(this.pos);

    }

    public void move(){
        rayCaster.updateRaysPos(pos);
        rayCaster.updateRaysDirection(direction);
    }

    public RayCaster getRaycaster(){
        return rayCaster;
    }

    public Point getPos(){
        return this.pos;
    }


}
