package Interface.menu;

import Interface.PrincipalPanel;
import Interface.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    JButton playLevelsButton;
    JButton exitButton;
    JButton settingsButton;
    JButton levelMakerButton;

    public MainMenu() {
        setLayout(null);
        this.setBackground(Color.MAGENTA);

        playLevelsButton = new JButton("Play Levels");
        playLevelsButton.setBounds(Window.getInstance().getWidth()/2-50, Window.getInstance().getHeight()/6, 100, 30);
        playLevelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("PlayLevels");
            }
        });

        add(playLevelsButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(650, 720, 100, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(exitButton);

        settingsButton = new JButton("Settings");
        settingsButton.setBounds(Window.getInstance().getWidth()/2-50, Window.getInstance().getHeight()/3, 100, 30);
        add(settingsButton);

        levelMakerButton = new JButton("Level Maker");
        levelMakerButton.setBounds(Window.getInstance().getWidth()/2-50, Window.getInstance().getHeight()/4, 100, 30);
        add(levelMakerButton);



    }
}
