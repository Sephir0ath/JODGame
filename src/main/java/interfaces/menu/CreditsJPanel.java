package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import main.java.interfaces.Window;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CreditsJPanel extends JPanel {
	private Font pixelFont;
	private Image backgroundImage;
	
	public CreditsJPanel(Font pixelFont) {
			this.setLayout(null);
			this.pixelFont = pixelFont;

		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		String[] CreditsStrings = {
				"Authors:",
				"Juan Felipe Raysz Muñoz (Sephir)",
				"Oliver Isaías Peñailillo Sanzana (Pyrrss)",
				"Diego Emilio Rebollo García (diego-52h)"
		};
		
		JButton backButton = createButton("Back", this.pixelFont);
		
		for (int i = 0; i < CreditsStrings.length; i++) {
			JLabel label = new JLabel(CreditsStrings[i]);
			
			label.setBounds(Window.getInstance().getWidth()/2-300, Window.getInstance().getHeight()/2+(i*30), 600, 20);
			label.setFont(pixelFont.deriveFont(14f));
			label.setForeground(Color.WHITE);
			
			this.add(label);
		}
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("Settings");
			}
		});
		
		backButton.setBounds(650, 720, 100, 50);
		this.add(backButton);
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
	}
}