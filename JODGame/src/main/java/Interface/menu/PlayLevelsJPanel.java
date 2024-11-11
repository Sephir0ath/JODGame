package Interface.menu;

import Interface.PrincipalPanel;
import javax.swing.*;
import java.awt.*;


public class PlayLevelsJPanel extends JPanel {

    public PlayLevelsJPanel() {
        this.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            JButton levelButton = new JButton(String.valueOf(i+1));
            int level = i;
            levelButton.addActionListener(e -> PrincipalPanel.getInstance().showPanel("map" + level));
            this.add(levelButton);
        }

        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(e -> PrincipalPanel.getInstance().showPanel("MainMenu"));
        this.add(goBackButton);

    }
}
