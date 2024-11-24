package main.java.logic;

public abstract class GameNode
{
	protected double direction;
	protected Vector2 position;
	
	protected RaycastComponent raycastComponent;
	protected GraphicsComponent graphicsComponent;
	protected CollisionComponent collisionComponent;
	
	public GameNode(Vector2 position)
	{
		this.direction = 0;
		this.position = position;
	}
	
	public double getDirection() { return this.direction; }
	public Vector2 getPosition() { return this.position; }
	
	public RaycastComponent getRaycastComponent() { return this.raycastComponent; }
	public GraphicsComponent getGraphicsComponent() { return this.graphicsComponent; }
	public CollisionComponent getCollisionComponent() { return this.collisionComponent; }
	
	public void setDirection(double direction) { this.direction = direction; }
	public void setPosition(Vector2 position) { this.position = position; }
	
	public void incrementDirection(double direction) { this.direction += direction; }
	
	public void setRaycastComponent(RaycastComponent raycastComponent) { this.raycastComponent = raycastComponent; }
	public void setGraphicsComponent(GraphicsComponent graphicsComponent) { this.graphicsComponent = graphicsComponent; }
	public void setCollisionComponent(CollisionComponent collisionComponent) { this.collisionComponent = collisionComponent; }
	
	public abstract void update(double time);
	public abstract void manageCollision(GameNode node);
	public abstract void manageIntersection(GameNode node);
}