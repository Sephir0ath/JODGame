package main.java.interfaces;

import main.java.logic.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class PlayerController implements KeyListener
{
	private final Player player;
	private final Set<Integer> pressedKeys;
	
	public PlayerController(Player player)
	{
		this.player = player;
		
		this.pressedKeys = new HashSet<>();
	}
	
	@Override
	public void keyTyped(KeyEvent event)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE && PrincipalPanel.getInstance().isActualPanelAMap())
		{
			PrincipalPanel.getInstance().showPanel("MainMenu");
		}
		
		else
		{
			this.pressedKeys.add(event.getKeyCode());
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		pressedKeys.remove(event.getKeyCode());
		
		if(pressedKeys.isEmpty())
			this.player.setVelocity(0);
		
		else
			this.handlePlayerMovement();
	}
	
	public void handlePlayerMovement()
	{
		int velocity = 0;
		int directionChange = 0;
		
		if(pressedKeys.contains(KeyEvent.VK_A))
			directionChange -= 1;
		
		if(pressedKeys.contains(KeyEvent.VK_D))
			directionChange += 1;
		
		if(pressedKeys.contains(KeyEvent.VK_W))
			velocity = 100;
		
		if(pressedKeys.contains(KeyEvent.VK_S))
			velocity = -100;
		
		player.addToDirection(directionChange);
		player.setVelocity(velocity);
	}
}