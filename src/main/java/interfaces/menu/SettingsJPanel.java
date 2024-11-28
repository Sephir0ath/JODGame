package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import main.java.interfaces.Window;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SettingsJPanel extends JPanel {
	private Font pixelFont;
	private Image backgroundImage;
	
	public SettingsJPanel(Font pixelFont) {
		this.setLayout(null);
		this.pixelFont = pixelFont;
		
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JButton backButton = createButton("Back", pixelFont);
		JButton creditsButton = createButton("Credits", pixelFont);
		
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
		
		backButton.setBounds(650, 720, 100, 50);
		this.add(backButton);
		
		creditsButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/4, 400, 50);
		this.add(creditsButton);
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