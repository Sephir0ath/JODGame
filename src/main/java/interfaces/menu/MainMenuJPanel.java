package main.java.interfaces.menu;

import main.java.interfaces.SoundPlayer;
import main.java.interfaces.Window;
import main.java.interfaces.PrincipalPanel;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenuJPanel extends JPanel {
	JButton playLevelsButton;
	JButton exitButton;
	JButton settingsButton;
	JButton levelMakerButton;
	
	private Font pixelFont;
	private Image backgroundImage;
	private Image playerImage;
	private Image enemyImage;
	private static SoundPlayer mainMusic;

	public MainMenuJPanel(Font pixelFont) {
		setLayout(null);
		mainMusic = new SoundPlayer();
		
		this.setBackground(Color.BLACK);
		this.pixelFont = pixelFont;
		mainMusic.play("src/main/resources/MusicaMain.wav");

		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
			playerImage = ImageIO.read(new File("src/main/resources/player-menu.png"));
			enemyImage = ImageIO.read(new File("src/main/resources/enemy-menu.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		playLevelsButton = createButton("Play Levels", this.pixelFont);
		playLevelsButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/6, 400, 50);
		playLevelsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("PlayLevels");
				mainMusic.stop();
			}

		});
		
		add(playLevelsButton);
		
		exitButton = createButton("Exit", this.pixelFont);
		exitButton.setBounds(650, 720, 100, 50);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		
		add(exitButton);
		
		settingsButton = createButton("Settings", this.pixelFont);
		settingsButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/3, 400, 50);
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("Settings");
				mainMusic.stop();
			}
		});
		add(settingsButton);
		
		// levelMakerButton = createButton("Level Maker", this.pixelFont);
		// levelMakerButton.setBounds(Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/4, 400, 50);
		
		// add(levelMakerButton);
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

		if(playerImage != null)
			g.drawImage(playerImage, Window.getInstance().getWidth()/2-200, Window.getInstance().getHeight()/4 + 200, 200, 200, null);

		if(enemyImage != null)
			g.drawImage(enemyImage, Window.getInstance().getWidth()/2, Window.getInstance().getHeight()/4 + 200, 200, 200,null);
	}

	public static void playMusic(){
		mainMusic.play("src/main/resources/MusicaMain.wav");
		mainMusic.loop();
	}
}