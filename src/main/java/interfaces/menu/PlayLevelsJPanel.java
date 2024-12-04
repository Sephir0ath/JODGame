package main.java.interfaces.menu;

import main.java.interfaces.PrincipalPanel;
import main.java.interfaces.SoundPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PlayLevelsJPanel extends JPanel {
	private Font pixelFont;
	private static SoundPlayer soundPlayer;
	
	public PlayLevelsJPanel(Font pixelFont) {
		this.setLayout(new GridLayout(2, 3));
		this.soundPlayer = new SoundPlayer();
		this.pixelFont = pixelFont;
		
		for (int i = 0; i < 7; i++) {
			JButton levelButton = createButton(String.valueOf(i+1), this.pixelFont);
			int level = i;
			levelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PrincipalPanel.getInstance().showPanel("map" + level);
					MainMenuJPanel.stopMusic();
//					switch (level){
//						case 1:
//							soundPlayer.play("src/main/resources/MusicaCitaPelea.wav");
//							soundPlayer.loop();
//							break;
//						case 2:
//							soundPlayer.play("src/main/resources/MusicaChiste.wav");
//							soundPlayer.loop();
//							break;
//						case 4:
//							soundPlayer.play("src/main/resources/MusicaDelCovenant.wav");
//							soundPlayer.loop();
//							break;
//						case 6:
//							soundPlayer.play("src/main/resources/MusicaDeAtraco.wav");
//							soundPlayer.loop();
//							break;
//					}



				}
			});

			this.add(levelButton);
		}
		
		JButton goBackButton = createButton("Back", this.pixelFont);
		goBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrincipalPanel.getInstance().showPanel("MainMenu");
//				MainMenuJPanel.playMusic();
			}
		});
		this.add(goBackButton);
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

	public static void stopMusic(){
		soundPlayer.stop();
	}
}