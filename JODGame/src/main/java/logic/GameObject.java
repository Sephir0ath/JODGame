package logic;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {
    protected final ImageIcon texture;
    public GameObject(ImageIcon texture) {
        this.texture = texture;
    }

    public ImageIcon getTexture(){
        return texture;
    }
/*
    public Point getPosition(){
        returnsd position;
    }
*/
}
