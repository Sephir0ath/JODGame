package Interface.menu;

import Interface.PrincipalPanel;
import Interface.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditsJPanel extends JPanel {
    public CreditsJPanel() {
        this.setLayout(null);
        String[] CreditsStrings = {"Authors:", "Juan Felipe Raysz Muñoz (Sephir)", "Oliver Isaías Peñailillo Sanzana (Pyrrss)", "Diego Emilio Rebollo García (Diego52_H)"};
        JButton backButton = new JButton("Back");

        for (int i = 0; i < CreditsStrings.length; i++) {
            JLabel label = new JLabel(CreditsStrings[i]);
            label.setBounds(Window.getInstance().getWidth()/2-300, Window.getInstance().getHeight()/2+(i*30), 600, 20);
            label.setFont(label.getFont().deriveFont(20f));
            this.add(label);
        }

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrincipalPanel.getInstance().showPanel("Settings");
            }
        });


        backButton.setBounds(650, 720, 100, 30);
        this.add(backButton);
    }
}
