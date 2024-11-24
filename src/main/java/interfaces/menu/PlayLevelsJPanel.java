package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import javax.swing.*;
import java.awt.*;


public class PlayLevelsJPanel extends JPanel {
    private Font pixelFont;
    public PlayLevelsJPanel(Font pixelFont) {
        this.setLayout(new GridLayout(2, 3));
        this.pixelFont = pixelFont;
        for (int i = 0; i < 7; i++) {
            JButton levelButton = createButton(String.valueOf(i+1), this.pixelFont);
            int level = i;
            levelButton.addActionListener(e -> PrincipalPanel.getInstance().showPanel("map" + level));
            this.add(levelButton);
        }

        JButton goBackButton = createButton("Back", this.pixelFont);
        goBackButton.addActionListener(e -> PrincipalPanel.getInstance().showPanel("MainMenu"));
        this.add(goBackButton);

    }

    private JButton createButton(String text, Font pixelFont) {

        JButton button = new JButton(text);
        button.setFont(pixelFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 7));
        return button;

    }
}
