package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import main.java.interfaces.SoundManager;
import main.java.interfaces.SoundPlayer;
import main.java.interfaces.Window;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

public class LevelLostJPanel extends JPanel {
	private Font pixelFont;
	private Image backgroundImage;
	private static int currentMapIndex;

	SoundManager soundManager;
	public LevelLostJPanel(Font pixelFont)
	{
		this.setLayout(null);
		this.pixelFont = pixelFont;

		soundManager = new SoundManager();
		
		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JLabel message = new JLabel("Level lost");
		
		message.setFont(pixelFont);
		message.setForeground(Color.WHITE);
		message.setBackground(Color.BLACK);
		message.setVerticalAlignment(JLabel.CENTER);
		message.setHorizontalAlignment(JLabel.CENTER);
		
		message.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/10, 400, 50);
		
		add(message);
		
		JButton menuButton = createButton("Return to menu", this.pixelFont);
		menuButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/6, 400, 50);
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("MainMenu");
				PlayLevelsJPanel.stopMusic();
				MainMenuJPanel.playMusic();

			}
		});
		add(menuButton);

		JButton restartButton = createButton("Restart level", this.pixelFont);
		restartButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/4, 400, 50);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().restartCurrentLevel(currentMapIndex);
//				soundManager.playLevelMusic(currentMapIndex);
			}
		});
		add(restartButton);

		JButton levelsButton = createButton("Play another level", this.pixelFont);
		levelsButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/2, 400, 50);
		levelsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("PlayLevels");
				PlayLevelsJPanel.stopMusic();
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

	public static void setMapIndex(int mapIndex)
	{
		currentMapIndex = mapIndex;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, null);
	}
}