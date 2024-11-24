package main.java.logic;

public class Enemy extends GameNode
{
	private double velocity;
	private double direction;
	private double targetDirection;
	
	private Vector2 target;
	private MovementZone movementZone;
	
	private RayCaster rayCaster;
	
	public Enemy(Vector2 position, MovementZone movementZone)
	{
		super(position);
		
		this.velocity = 50;
		this.direction = 0;
		
		this.movementZone = movementZone;
		
		this.rayCaster = new RayCaster(position);
		
		this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	public MovementZone getMovementZone()
	{
		return this.movementZone;
	}
	
	public RayCaster getRayCaster()
	{
		return this.rayCaster;
	}
	
	public void setTarget(Vector2 target)
	{
		Vector2 directionVector = Vector2.subtract(target, this.position);
		
		this.target = target;
		this.targetDirection = Math.toDegrees(Math.acos(Vector2.dot(directionVector, new Vector2(1, 0)) / directionVector.getSize()));
		
		if(directionVector.y < 0)
			this.targetDirection = 360 - this.targetDirection;
	}

	public double getDirection() { return this.direction; }

	@Override
	public void update(double time)
	{
		if(Math.abs(this.targetDirection - this.direction) > 0.5)
		{
			double sign = (this.targetDirection - this.direction) / Math.abs(this.targetDirection - this.direction);
			
			this.direction += sign * 0.5;
			this.rayCaster.setRaysDirection(this.direction);
			
			if(Math.abs(this.targetDirection - this.direction) < 0.5)
			{
				this.direction = this.targetDirection;
				this.rayCaster.setRaysDirection(this.direction);
			}
		}
		
		else
		{
			this.position.x = this.position.x + ((velocity * time) * Math.cos(Math.toRadians(direction)));
			this.position.y = this.position.y + ((velocity * time) * Math.sin(Math.toRadians(direction)));
			
			this.rayCaster.setRaysSource(position);
		}
		
		if(Vector2.subtract(this.target, position).getSize() < 5)
			this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		if(node instanceof Player)
		{
			// El jugador pierde
		}
		
		else
		{
			double aux = this.velocity;
			
			this.velocity = -aux;
			this.update(0.016);
			this.velocity = +aux;
			
			this.setTarget(this.movementZone.chooseLocation(this.position));
		}
	}
}