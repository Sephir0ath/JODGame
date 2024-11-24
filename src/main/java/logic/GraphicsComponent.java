package main.java.logic;

import main.java.interfaces.RotatedIcon;

import javax.swing.ImageIcon;
import java.awt.*;


public class GraphicsComponent
{
	private Vector2 dims;
	private GameNode owner;

	private RotatedIcon texture;

	public GraphicsComponent(GameNode owner, RotatedIcon texture, Vector2 dims)
	{
		this.dims = dims;
		this.owner = owner;

		this.texture = texture;
	}

	public GameNode getOwner()
	{
		return this.owner;
	}

	public Vector2 getDims()
	{
		return this.dims;
	}

	public Image getTexture() { return this.texture.getImage(); }

	public RotatedIcon getRotatedIcon() { return this.texture; }

	public void rotateSprite(double angle) {
		texture.setDegrees(angle);
	}
}