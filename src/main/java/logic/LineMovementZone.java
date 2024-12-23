package main.java.logic;

public class LineMovementZone extends MovementZone
{
	private Vector2 pointA;
	private Vector2 pointB;
	
	public LineMovementZone(Vector2 pointA, Vector2 pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	@Override
	public Vector2 chooseLocation(Vector2 position)
	{
		Vector2 location = null;
		
		if(Vector2.subtract(this.pointA, position).getSize() < 5)
			location = this.pointB;
		
		else
			location = this.pointA;
		
		return location;
	}
	
	public Vector2 getPointA()
	{
		return this.pointA;
	}
	
	public Vector2 getPointB()
	{
		return this.pointB;
	}
}