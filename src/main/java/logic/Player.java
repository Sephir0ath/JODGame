package main.java.logic;

public class Player extends GameNode
{
	private RayCaster rayCaster;
	
	private double velocity;
	private double direction;

	public Player(Vector2 position)
	{
		super(position);
		
		//this.rayCaster = new RayCaster(this.position);
	}
	
	public double getVelocity()
	{
		return this.velocity;
	}
	
	public double getDirection()
	{
		return this.direction;
	}
	
//	public RayCaster getRayCaster()
//	{
//		return this.rayCaster;
//	}
	
	public void addToDirection(double degrees)
	{
		this.direction += degrees;
		this.direction %= 360;
		
		//this.rayCaster.setRaysDirection(direction);
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
		
		//this.rayCaster.setRaysSource(position);
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		double aux = this.velocity;
		
		this.velocity = -aux;
		this.update(0.016);
		this.velocity = +aux;
	}
}