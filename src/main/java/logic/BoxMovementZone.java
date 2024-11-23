package logic;

public class BoxMovementZone extends MovementZone
{
	public double sizeX;
	public double sizeY;
	
	public Vector2 center;
	
	public BoxMovementZone(double sizeX, double sizeY, Vector2 center)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.center = center;
	}
	
	@Override
	public Vector2 chooseLocation(Vector2 position)
	{
		Vector2 location = new Vector2();
		
		int sign1 = (Math.random() < 0.5) ? -1 : 1;
		int sign2 = (Math.random() < 0.5) ? -1 : 1;
		
		location.x = this.center.x + (sign1 * (Math.random() * (this.sizeX / 2)));
		location.y = this.center.y + (sign2 * (Math.random() * (this.sizeY / 2)));
		
		return location;
	}
}