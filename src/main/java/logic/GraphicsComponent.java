package logic;

import javax.swing.ImageIcon;

public class GraphicsComponent
{
	private Vector2 dims;
	private GameNode owner;
	
	private ImageIcon texture;
	
	public GraphicsComponent(GameNode owner, ImageIcon texture, Vector2 dims)
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
	
	public ImageIcon getTexture()
	{
		return this.texture;
	}
}