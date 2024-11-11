package Interface.menu;

import Interface.PrincipalPanel;
import logic.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.Pipe;
import java.util.ArrayList;

public class PlayLevelsJPanel extends JPanel {
    private ArrayList<JButton> buttons;
    public PlayLevelsJPanel() {
        this.setLayout(new GridLayout(2, 3));

        buttons = new ArrayList<>();
        int i;
        for (i = 0; i < 5; i++){
            buttons.add(new JButton(""+i));
            int j = i;
            buttons.get(i).addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e){
                    PrincipalPanel.getInstance().showPanel("map"+j);
                }
            });

            this.add(new JButton(""+i));

        }

    }

}
