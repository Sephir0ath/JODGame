package logic;

import java.util.ArrayList;

public class RayCaster
{
	private final int MAX_DISTANCE = 200;
	
	private ArrayList<Ray> rays;
	
	public RayCaster(Vector2 source)
	{
		this.rays = new ArrayList<>();
		
		for(int i = -22; i < 22; i++)
			this.rays.add(new Ray(source, i));
	}

	public void setRaysSource(Vector2 source)
	{
		for(int i = -22; i < 22; i++)
			this.rays.get(i + 22).setSource(source);
	}

	public void setRaysDirection(double degrees)
	{
		for (int i = -22; i < 22; i++)
			this.rays.get(i + 22).setDirection(degrees + i);
	}
	
	public ArrayList<Line> getIntersections(ArrayList<Line> collisionLines)
	{
		ArrayList<Line> segments = new ArrayList<>();
		
		for(Ray ray : this.rays)
		{
			double minDistance = 10000;
			
			Vector2 nearestIntersection = null;
			
			for(Line collisionLine : collisionLines)
			{
				Vector2 intersection = ray.cast(collisionLine);
				
				if(intersection != null)
				{
					double x = ray.getSource().x - intersection.x;
					double y = ray.getSource().y - intersection.y;
					
					double distance = Math.sqrt((x * x) + (y * y));
					
					if((distance < minDistance) && (distance < MAX_DISTANCE))
					{
						minDistance = distance;
						
						nearestIntersection = intersection;
					}
				}
			}
			
			if(nearestIntersection == null)
			{
				double proyectionX = ray.getSource().x + (ray.getDirection().x * MAX_DISTANCE);
				double proyectionY = ray.getSource().y + (ray.getDirection().y * MAX_DISTANCE);
				
				nearestIntersection = new Vector2(proyectionX, proyectionY);
			}
			
			segments.add(new Line(ray.getSource(), nearestIntersection));
		}
		
		return segments;
	}
}