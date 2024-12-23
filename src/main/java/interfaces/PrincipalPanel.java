package main.java.interfaces;

import main.java.interfaces.menu.*;

import java.awt.*;

import javax.swing.*;

import java.io.File;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrincipalPanel extends JPanel
{
	private static PrincipalPanel instance;
	
	private CardLayout cardLayout;
	private JPanel actualPanel;
	private Font pixelFont;
	
	private MapLoader map;
	private InputManager inputManager;
	
	private ArrayList<MapLoader> maps;
	
	public PrincipalPanel()
	{
		instance = this;
		
		maps = new ArrayList<>();
		cardLayout = new CardLayout();
		
		this.setLayout(cardLayout);
		
		try
		{
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font.ttf")).deriveFont(18f);
		}
		
		catch(Exception e)
		{
			pixelFont = new Font("Monospaced", Font.BOLD, 18);
		}
		
		// -------- Menú --------------
		MainMenuJPanel mainMenuJPanel = new MainMenuJPanel(pixelFont);
		PlayLevelsJPanel playLevelsJPanel = new PlayLevelsJPanel(pixelFont);
		CreditsJPanel creditsJPanel = new CreditsJPanel(pixelFont);
		SettingsJPanel settingsJPanel = new SettingsJPanel(pixelFont);
		LevelLostJPanel levelLostJPanel = new LevelLostJPanel(pixelFont);
		LevelCompletedJPanel levelCompletedJPanel = new LevelCompletedJPanel(pixelFont);
		ControlsJPanel controlsJPanel = new ControlsJPanel(pixelFont);

		add(mainMenuJPanel, "MainMenu"); // Menú principal
		add(playLevelsJPanel, "PlayLevels"); // Menú de los niveles pre-hechos
		add(settingsJPanel, "Settings");
		add(creditsJPanel, "Credits");
		add(levelLostJPanel, "LevelLost");
		add(levelCompletedJPanel, "LevelCompleted");
		add(controlsJPanel, "Controls");

		// -------- Levels ------------
		for (int i = 0; i < 7; i++)
		{
			this.maps.add(new MapLoader("src/main/resources/map-" + i + ".txt", i));
			
			this.add(maps.get(i), "map" + i);
		}
		
		// Esto repinta los niveles y actualiza la posición del jugador
		ScheduledExecutorService playerScheduler = new ScheduledThreadPoolExecutor(1);
		playerScheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run()
			{
				if(map != null)
				{
					map.setDownKeys(inputManager.getDownKeys());
					map.repaint();
				}
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
		
		setFocusable(true);
		requestFocusInWindow();
		
		this.inputManager = new InputManager(this);
		this.addKeyListener(this.inputManager);
	}
	
	public void showPanel(String panelName)
	{
		cardLayout.show(this, panelName);
		
		if(panelName.contains("map"))
		{
			int mapID = Character.getNumericValue(panelName.charAt(panelName.length() - 1));

			if(map != null)
			{
				map.stopMusic();
			}

			map = this.maps.get(mapID);
			map.playMusic();
		}
		
		else
			if (map != null) {
				map.stopMusic();
				map = null;
			}
	}

	public void restartCurrentLevel(int mapIndex)
	{

		MapLoader newMap = new MapLoader("src/main/resources/map-" + mapIndex + ".txt", mapIndex);
		maps.set(mapIndex, newMap);
		this.add(newMap, "map" + mapIndex);

		this.add(newMap, "map" + mapIndex);
		showPanel("map" + mapIndex);

	}

	public static PrincipalPanel getInstance()
	{
		if(instance == null)
			instance = new PrincipalPanel();
		
		return instance;
	}

}