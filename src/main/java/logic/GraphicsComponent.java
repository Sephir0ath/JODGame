package main.java.logic;

import java.awt.*;

import main.java.interfaces.RotatedIcon;

public class GraphicsComponent extends Component
{
	private Vector2 dims;
	private RotatedIcon texture;

	public GraphicsComponent(GameNode owner, RotatedIcon texture, Vector2 dims)
	{
		super(owner);
		
		this.dims = dims;
		this.texture = texture;
	}
	
	public Vector2 getDims()
	{
		return this.dims;
	}

	public RotatedIcon getTexture()
	{
		return this.texture;
	}
}