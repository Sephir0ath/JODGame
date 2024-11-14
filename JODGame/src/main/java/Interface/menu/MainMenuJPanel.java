package Interface.menu;

import Interface.PrincipalPanel;
import Interface.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuJPanel extends JPanel {
    JButton playLevelsButton;
    JButton levelMakerButton;
    JButton settingsButton;
    JButton exitButton;

    public MainMenuJPanel() {
        setLayout(null);
        this.setBackground(Color.ORANGE);

        playLevelsButton = new JButton("Play Levels");
        playLevelsButton.setBounds(Window.getInstance().getWidth()/2-75, Window.getInstance().getHeight()/6, 150, 30);
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
        settingsButton.setBounds(Window.getInstance().getWidth()/2-75, Window.getInstance().getHeight()/3, 150, 30);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("Settings");
            }
        });
        add(settingsButton);

        levelMakerButton = new JButton("Level Maker");
        levelMakerButton.setBounds(Window.getInstance().getWidth()/2-75, Window.getInstance().getHeight()/4, 150, 30);
        add(levelMakerButton);



    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(getClass().getClassLoader().getResource("PlayLevelsButton.png")).getImage(), Window.getInstance().getWidth()/2-75, Window.getInstance().getHeight()/3, 150,30  , null);

    }
}
