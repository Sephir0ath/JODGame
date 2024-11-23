package logic;

public class Line
{
	private Vector2 pointA;
	private Vector2 pointB;
	
	public Line(Vector2 pointA, Vector2 pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public Vector2 getPointA() { return this.pointA; }
	public Vector2 getPointB() { return this.pointB; }
}