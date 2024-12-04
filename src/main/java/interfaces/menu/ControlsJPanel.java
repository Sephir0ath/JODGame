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

public class ControlsJPanel extends JPanel {
    private Font pixelFont;
    private Image backgroundImage;
    private Image awsdImage;
    private Image spacebarImage;
    private Image shiftImage;

    public ControlsJPanel(Font pixelFont) {

        this.setLayout(null);
        this.pixelFont = pixelFont;

        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
            awsdImage = ImageIO.read(new File("src/main/resources/awsd.png"));
            spacebarImage = ImageIO.read(new File("src/main/resources/spacebar.png"));
            shiftImage = ImageIO.read(new File("src/main/resources/shift.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        JButton backButton = createButton("Back", pixelFont);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("MainMenu");
//                MainMenuJPanel.playMusic();
            }
        });

        backButton.setBounds(650, 720, 100, 50);
        this.add(backButton);

        JLabel movement = new JLabel("¡Movement!");

        movement.setFont(pixelFont);
        movement.setForeground(Color.WHITE);
        movement.setBackground(Color.BLACK);
        movement.setVerticalAlignment(JLabel.CENTER);
        movement.setHorizontalAlignment(JLabel.CENTER);

        movement.setBounds(400, 150, 400, 50);

        add(movement);

        JLabel shoot = new JLabel("¡Shoot!");

        shoot.setFont(pixelFont);
        shoot.setForeground(Color.WHITE);
        shoot.setBackground(Color.BLACK);
        shoot.setVerticalAlignment(JLabel.CENTER);
        shoot.setHorizontalAlignment(JLabel.CENTER);

        shoot.setBounds(400, 350, 400, 50);

        add(shoot);

        JLabel dash = new JLabel("¡Dash!");

        dash.setFont(pixelFont);
        dash.setForeground(Color.WHITE);
        dash.setBackground(Color.BLACK);
        dash.setVerticalAlignment(JLabel.CENTER);
        dash.setHorizontalAlignment(JLabel.CENTER);

        dash.setBounds(400, 550, 400, 50);

        add(dash);








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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, null);

        if(awsdImage != null)
            g.drawImage(awsdImage, 100, 50, 200, 200,null);

        if(spacebarImage != null)
            g.drawImage(spacebarImage, 15, 175, 400, 400, null);

        if(shiftImage != null)
            g.drawImage(shiftImage, 125, 515, 200, 100, null);
    }

}
