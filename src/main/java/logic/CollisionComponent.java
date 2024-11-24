package main.java.logic;

import java.util.ArrayList;

public class CollisionComponent
{
	private Vector2 dims;
	private GameNode owner;
	
	public CollisionComponent(GameNode owner, Vector2 dims)
	{
		this.dims = dims;
		this.owner = owner;
	}
	
	public boolean contains(Vector2 point)
	{
		Vector2 position = this.owner.getPosition();
		
		boolean intersectionOverX;
		{
			double pointA = position.x - (this.dims.x / 2);
			double pointB = position.x + (this.dims.x / 2);
			
			intersectionOverX = (pointA <= point.x) && (point.x <= pointB);
		}
		
		boolean intersectionOverY;
		{
			double pointA = position.y - (this.dims.y / 2);
			double pointB = position.y + (this.dims.y / 2);
			
			intersectionOverY = (pointA <= point.y) && (point.y <= pointB);
		}
		
		return intersectionOverX && intersectionOverY;
	}
	
	public static boolean areColliding(CollisionComponent collisionComponentA, CollisionComponent collisionComponentB)
	{
		Vector2 dimsA = collisionComponentA.dims;
		Vector2 dimsB = collisionComponentB.dims;
		
		Vector2 positionA = collisionComponentA.owner.getPosition();
		Vector2 positionB = collisionComponentB.owner.getPosition();
		
		boolean intersectionOverX;
		{
			double pointA1 = positionA.x - (dimsA.x / 2);
			double pointA2 = positionA.x + (dimsA.x / 2);
			
			double pointB1 = positionB.x - (dimsB.x / 2);
			double pointB2 = positionB.x + (dimsB.x / 2);
			
			intersectionOverX = ((pointA1 < pointB1) && (pointB1 < pointA2)) || ((pointB1 < pointA1) && (pointA1 < pointB2));
		}
		
		boolean intersectionOverY;
		{
			double pointA1 = positionA.y - (dimsA.y / 2);
			double pointA2 = positionA.y + (dimsA.y / 2);
			
			double pointB1 = positionB.y - (dimsB.y / 2);
			double pointB2 = positionB.y + (dimsB.y / 2);
			
			intersectionOverY = ((pointA1 < pointB1) && (pointB1 < pointA2)) || ((pointB1 < pointA1) && (pointA1 < pointB2));
		}
		
		return intersectionOverX && intersectionOverY;
	}
	
	public Vector2 getDims()
	{
		return this.dims;
	}
	
	public ArrayList<Line> getOutline()
	{
		Vector2 position = this.owner.getPosition();
		
		ArrayList<Line> outline = new ArrayList<>();
		{
			Vector2 pointTL = new Vector2(position.x - (this.dims.x / 2), position.y - (this.dims.y / 2));
			Vector2 pointTR = new Vector2(position.x + (this.dims.x / 2), position.y - (this.dims.y / 2));
			Vector2 pointBL = new Vector2(position.x - (this.dims.x / 2), position.y + (this.dims.y / 2));
			Vector2 pointBR = new Vector2(position.x + (this.dims.x / 2), position.y + (this.dims.y / 2));
			
			outline.add(new Line(pointTL, pointTR));
			outline.add(new Line(pointBL, pointBR));
			outline.add(new Line(pointTL, pointBL));
			outline.add(new Line(pointTR, pointBR));
		}
		
		return outline;
	}
}