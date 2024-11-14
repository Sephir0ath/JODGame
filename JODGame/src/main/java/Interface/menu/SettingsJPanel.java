package Interface.menu;

import Interface.PrincipalPanel;
import Interface.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsJPanel extends JPanel {
    public SettingsJPanel() {
        this.setLayout(null);
        this.setBackground(Color.orange);
        JButton backButton = new JButton("Back");
        JButton creditsButton = new JButton("Credits");

        creditsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("Credits");
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("MainMenu");
            }
        });


        backButton.setBounds(650, 720, 100, 30);
        this.add(backButton);

        creditsButton.setBounds(Window.getInstance().getWidth()/2-50, Window.getInstance().getHeight()/4, 100, 30);
        this.add(creditsButton);
    }
}
