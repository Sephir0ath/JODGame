package main.java.logic;

public class BoxMovementZone extends MovementZone
{
	public Vector2 dims;
	public Vector2 position;
	
	public BoxMovementZone(Vector2 position, Vector2 dims)
	{
		this.dims = dims;
		this.position = position;
	}
	
	@Override
	public Vector2 chooseLocation(Vector2 position)
	{
		Vector2 location = new Vector2();
		
		int sign1 = (Math.random() < 0.5) ? -1 : 1;
		int sign2 = (Math.random() < 0.5) ? -1 : 1;
		
		location.x = this.position.x + (sign1 * (Math.random() * (this.dims.x / 2)));
		location.y = this.position.y + (sign2 * (Math.random() * (this.dims.y / 2)));
		
		return location;
	}
}