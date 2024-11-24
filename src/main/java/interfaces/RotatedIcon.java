package main.java.interfaces;

import main.java.logic.*;

import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;

public class RotatedIcon implements Icon
{
	private Icon icon;
	private ImageIcon imageIcon;
	
	private double direction;
	
	private Vector2 dims;
	
	public RotatedIcon(ImageIcon icon)
	{
		this.direction = 0;
		
		this.icon = icon;
		this.imageIcon = icon;
	}
	
	public Icon getIcon()
	{
		return icon;
	}
	
	public double getDirection()
	{
		return direction;
	}
	
	public Vector2 getDims()
	{
		return this.dims;
	}
	
	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	
	public void setDims(Vector2 dims)
	{
		this.dims = dims;
	}
	
	@Override
	public int getIconWidth()
	{
		double radians = Math.toRadians(direction);
		
		double sin = Math.abs(Math.sin(radians));
		double cos = Math.abs(Math.cos(radians));
		
		int width = (int) ((icon.getIconWidth() * cos) + (icon.getIconHeight() * sin));
		
		return width;
	}
	
	@Override
	public int getIconHeight()
	{
		double radians = Math.toRadians(direction);
		
		double sin = Math.abs(Math.sin(radians));
		double cos = Math.abs(Math.cos(radians));
		
		int height = (int) ((icon.getIconWidth() * sin) + (icon.getIconHeight() * cos));
		
		return height;
	}
	
	public Image getImage()
	{
		return this.imageIcon.getImage();
	}
	
	/**
	 *  Paint the icons of this compound icon at the specified location
	 *
	 *  @param c The component on which the icon is painted
	 *  @param g the graphics context
	 *  @param x the X coordinate of the icon's top-left corner
	 *  @param y the Y coordinate of the icon's top-left corner
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g.create();
		
		int cWidth = (int) this.dims.x / 2;
		int cHeight = (int) this.dims.y / 2;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.rotate(Math.toRadians(direction), x + cWidth, y + cHeight);
		
		g2.drawImage(this.imageIcon.getImage(), x, y, (int) this.dims.x, (int) this.dims.y, null);
		g2.dispose();
	}
}