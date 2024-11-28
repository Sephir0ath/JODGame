package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import main.java.interfaces.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LevelCompletedJPanel extends JPanel {
    private Font pixelFont;
    private Image backgroundImage;


    public LevelCompletedJPanel(Font pixelFont)
    {
        this.setLayout(null);
        this.pixelFont = pixelFont;

        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        JButton menuButton = createButton("Return to menu", this.pixelFont);
        menuButton.setBounds(main.java.interfaces.Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/4, 400, 50);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("MainMenu");
            }
        });
        add(menuButton);

        JButton levelsButton = createButton("Play another level", this.pixelFont);
        levelsButton.setBounds(main.java.interfaces.Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/2, 400, 50);
        levelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("PlayLevels");
            }
        });
        add(levelsButton);

    }


    private JButton createButton(String text, Font pixelFont)
    {

        JButton button = new JButton(text);
        button.setFont(pixelFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 7));
        return button;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, null);
        }




    }
}
