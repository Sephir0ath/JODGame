package logic;

import javax.swing.*;
import java.awt.*;

public class Enemy extends GameObject{
    private RayCaster rayCaster;
    private Point pos;
    private int direction;

    public Enemy(ImageIcon img){
        super(img);
        rayCaster = new RayCaster(new Point(0,0));

    }

    public move(){
        rayCaster.updateRaysPos(pos);
        rayCaster.updateRaysDirection(direction);
    }

    public getRaycaster(){
        return rayCaster;
    }



}
