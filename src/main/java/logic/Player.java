package main.java.logic;

public class Player extends GameNode
{
	private double health;
	private double maxHealth;
	private double invincibleTimer;
	
	private double velocity;
	
	public Player(Vector2 position, double maxHealth)
	{
		super(position);
		
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.invincibleTimer = 2;
	}
	
	public double getHealth()
	{
		return this.health;
	}
	
	public double getMaxHealth()
	{
		return this.maxHealth;
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
		if(this.invincibleTimer > 0)
			this.invincibleTimer = Math.max(0, invincibleTimer - time);
		
		this.position.x += (velocity * time) * Math.cos(Math.toRadians(this.direction));
		this.position.y += (velocity * time) * Math.sin(Math.toRadians(this.direction));
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		if((node instanceof Enemy) && (invincibleTimer == 0))
		{
			this.health -= 1;
			this.invincibleTimer = 2;
		}
		
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