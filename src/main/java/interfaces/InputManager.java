package main.java.interfaces;

import main.java.interfaces.menu.MainMenuJPanel;
import main.java.interfaces.menu.PlayLevelsJPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class InputManager implements KeyListener
{
	private PrincipalPanel owner;
	
	private Set<Integer> downKeys;
	
	public InputManager(PrincipalPanel owner)
	{
		this.owner = owner;
		
		this.downKeys = new HashSet<>();
	}
	
	public Set<Integer> getDownKeys()
	{
		return this.downKeys;
	}
	
	@Override
	public void keyTyped(KeyEvent event)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			this.owner.showPanel("MainMenu");

			PlayLevelsJPanel.stopMusic();

			MainMenuJPanel.playMusic();
			return;
		}
		
		this.downKeys.add(event.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		this.downKeys.remove(event.getKeyCode());
	}
}