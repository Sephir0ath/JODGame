package main.java.interfaces;

import java.awt.*;
import javax.swing.*;

public class Window extends JFrame
{
	private static Window instance;
	
	private Window()
	{
		instance = this;
		
		this.getContentPane().setPreferredSize(new Dimension(800, 800));
		this.pack();
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new PrincipalPanel());
		
		this.setVisible(true);
	}
	
	public static synchronized Window getInstance()
	{
		if(instance == null)
			instance = new Window();
		
		return instance;
	}
}