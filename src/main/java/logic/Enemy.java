package main.java.logic;

import main.java.interfaces.MapLoader;

public class Enemy extends GameNode
{
	private double velocity;
	private double targetDirection;
	
	private Vector2 target;
	private MovementZone movementZone;

	private double health;
	private double maxHealth;
	private double invincibleTimer;

	MapLoader mapLoader;
	public Enemy(Vector2 position, MovementZone movementZone, double maxHealth, MapLoader mapLoader)
	{
		super(position);

		this.mapLoader = mapLoader;

		this.velocity = 100;
		this.movementZone = movementZone;

		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.invincibleTimer = 2;
		
		this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	public MovementZone getMovementZone()
	{
		return this.movementZone;
	}

	public double getHealth()
	{
		return this.health;
	}

	public double getMaxHealth()
	{
		return this.maxHealth;
	}

	public void setTarget(Vector2 target)
	{
		Vector2 directionVector = Vector2.subtract(target, this.position);
		
		this.target = target;
		this.targetDirection = Math.toDegrees(Math.acos(Vector2.dot(directionVector, new Vector2(1, 0)) / directionVector.getSize()));
		
		if(directionVector.y < 0)
			this.targetDirection = 360 - this.targetDirection;
	}
	
	@Override
	public void update(double time)
	{
		if(this.invincibleTimer > 0)
			this.invincibleTimer = Math.max(0, invincibleTimer - time);

		if(this.direction != this.targetDirection)
		{
			double diff = this.targetDirection - this.direction;
			double sign = diff / Math.abs(diff);
			
			this.direction += sign * 0.75;
			
			if(Math.abs(this.targetDirection - this.direction) <= 0.5)
				this.direction = this.targetDirection;
			
			this.getRaycastComponent().setRaysDirection(this.direction);
		}
		
		else
		{
			this.position.x += (velocity * time) * Math.cos(Math.toRadians(this.direction));
			this.position.y += (velocity * time) * Math.sin(Math.toRadians(this.direction));
		}
		
		if(Vector2.subtract(this.target, position).getSize() < 5)
			this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		if((node instanceof Bullet) && (invincibleTimer == 0))
		{
			this.health -= 1;
			this.invincibleTimer = 2;
		}

		if(this.health <= 0)
		{
			mapLoader.removeNode(this);
		}

		double aux = this.velocity;
		
		this.velocity = -aux;
		this.update(0.016);
		this.velocity = +aux;
		
		this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	@Override
	public void manageIntersection(GameNode node)
	{
		if(node instanceof Player)
			this.setTarget(new Vector2(node.getPosition()));
	}
}