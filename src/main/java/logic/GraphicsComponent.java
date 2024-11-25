package main.java.logic;

import java.awt.Image;

public class GraphicsComponent extends Component
{
	private Vector2 dims;
	private Image texture;

	public GraphicsComponent(GameNode owner, Image texture, Vector2 dims)
	{
		super(owner);
		
		this.dims = dims;
		this.texture = texture;
	}
	
	public Vector2 getDims()
	{
		return this.dims;
	}

	public Image getTexture()
	{
		return this.texture;
	}
}