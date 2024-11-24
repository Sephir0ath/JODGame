package logic;

import javax.swing.ImageIcon;

public class GraphicsComponent extends Component
{
	private Vector2 dims;
	
	private ImageIcon texture;
	
	public GraphicsComponent(GameNode owner, ImageIcon texture, Vector2 dims)
	{
		super(owner);
		
		this.dims = dims;
		this.owner = owner;
		
		this.texture = texture;
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