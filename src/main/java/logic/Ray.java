package main.java.logic;

public class Ray
{
	private Vector2 source;
	private Vector2 direction;
	
	public Ray(Vector2 source, int degrees)
	{
		this.source = source;
		this.direction = new Vector2();
		
		this.setDirection(degrees);
	}

	public Vector2 cast(Line line)
	{
		double x1 = line.getPointA().x;
		double y1 = line.getPointA().y;
		
		double x2 = line.getPointB().x;
		double y2 = line.getPointB().y;
		
		double x3 = this.source.x;
		double y3 = this.source.y;
		
		double x4 = this.source.x + direction.x;
		double y4 = this.source.y + direction.y;
		
		double denominator = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
		
		if(denominator == 0)
		{
			return null;
		}
		
		double t = +((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
		double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;
		
		if((t >= 0) && (t <= 1) && (u >= 0))
		{
			Vector2 intersection = new Vector2();
			
			intersection.x = x1 + (t * (x2 - x1));
			intersection.y = y1 + (t * (y2 - y1));
			
			return intersection;
		}
		
		return null;
	}
	
	public Vector2 getSource()
	{
		return this.source;
	}
	
	public Vector2 getDirection()
	{
		return this.direction;
	}
	
	public void setSource(Vector2 source)
	{
		this.source = source;
	}
	
	public void setDirection(double degrees)
	{
		double radians = Math.toRadians(degrees);
		
		this.direction.x = Math.cos(radians);
		this.direction.y = Math.sin(radians);
	}
	
	public void setDirection(Vector2 direction)
	{
		this.direction = direction;
	}
}