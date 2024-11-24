package main.java.logic;

public class Enemy extends GameNode
{
	private double velocity;
	private double targetDirection;
	
	private Vector2 target;
	private MovementZone movementZone;
	
	public Enemy(Vector2 position, MovementZone movementZone)
	{
		super(position);
		
		this.velocity = 50;
		this.movementZone = movementZone;
		
		this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	public MovementZone getMovementZone()
	{
		return this.movementZone;
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
		if(Math.abs(this.targetDirection - this.direction) > 0.5)
		{
			double sign = (this.targetDirection - this.direction) / Math.abs(this.targetDirection - this.direction);
			
			this.direction += sign * 0.5;
			
			if(Math.abs(this.targetDirection - this.direction) < 0.5)
				this.direction = this.targetDirection;
			
			this.getRaycastComponent().setRaysDirection(this.direction);
		}
		
		else
		{
			this.position.x = this.position.x + ((velocity * time) * Math.cos(Math.toRadians(direction)));
			this.position.y = this.position.y + ((velocity * time) * Math.sin(Math.toRadians(direction)));
		}
		
		if(Vector2.subtract(this.target, position).getSize() < 5)
			this.setTarget(this.movementZone.chooseLocation(this.position));
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
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