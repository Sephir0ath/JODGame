package main.java.logic;

public class Vector2
{
	public double x;
	public double y;
	
	public Vector2()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 vector)
	{
		this.x = vector.x;
		this.y = vector.y;
	}
	
	public double getSize()
	{
		return Math.sqrt((this.x * this.x) + (this.y * this.y));
	}
	
	public Vector2 getNormal()
	{
		if(this.getSize() == 0)
			return this;
		
		double newX = this.x / this.getSize();
		double newY = this.y / this.getSize();
		
		return new Vector2(newX, newY);
	}
	
	public static double dot(Vector2 vectorA, Vector2 vectorB)
	{
		return (vectorA.x * vectorB.x) + (vectorA.y * vectorB.y);
	}
	
	public static Vector2 subtract(Vector2 vectorA, Vector2 vectorB)
	{
		return new Vector2(vectorA.x - vectorB.x, vectorA.y - vectorB.y);
	}
}