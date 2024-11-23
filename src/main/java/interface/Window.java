package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Window extends JFrame {
    private static Window instance;
    public Window(){
        super();
        instance = this;
        this.setSize(new Dimension(800, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PrincipalPanel());
        this.setResizable(false);
        this.setVisible(true);


    }

    public static synchronized Window getInstance(){
        if(instance == null){
            instance = new Window();
        }
        return instance;
    }

}
