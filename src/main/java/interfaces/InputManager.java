package main.java.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class InputManager implements KeyListener
{
	private Set<Integer> downKeys;
	
	public InputManager()
	{
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
		this.downKeys.add(event.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		this.downKeys.remove(event.getKeyCode());
	}
}