package main.java.logic;

public class Player extends GameNode
{
	private double velocity;
	
	public Player(Vector2 position)
	{
		super(position);
	}
	
	public double getVelocity()
	{
		return this.velocity;
	}
	
	public void addToDirection(double degrees)
	{
		this.direction += degrees;
		this.direction %= 360;
	}
	
	public void setVelocity(double velocity)
	{
		this.velocity = velocity;
	}
	
	@Override
	public void update(double time)
	{
		this.position.x = this.position.x + (velocity * time) * Math.cos(Math.toRadians(direction));
		this.position.y = this.position.y + (velocity * time) * Math.sin(Math.toRadians(direction));
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		double aux = this.velocity;
		
		this.velocity = -aux;
		this.update(0.016);
		this.velocity = +aux;
	}
	
	@Override
	public void manageIntersection(GameNode node)
	{
	}
}