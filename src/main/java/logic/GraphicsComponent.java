package logic;

import javax.swing.ImageIcon;

public class GraphicsComponent
{
	GameNode owner;
	
	private ImageIcon texture;
	
	public GraphicsComponent(GameNode owner, ImageIcon texture)
	{
		this.texture = texture;
	}
	
	public ImageIcon getTexture()
	{
		return this.texture;
	}
}