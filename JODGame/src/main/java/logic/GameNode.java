package logic;

import javax.swing.ImageIcon;

public abstract class GameNode
{
	protected Vector2 position;
	
	protected GraphicsComponent graphicsComponent;
	protected CollisionComponent collisionComponent;
	
	public GameNode(Vector2 position)
	{
		this.position = position;
	}
	
	public Vector2 getPosition() { return this.position; }
	
	public GraphicsComponent getGraphicsComponent() { return this.graphicsComponent; }
	public CollisionComponent getCollisionComponent() { return this.collisionComponent; }
	
	public void setPosition(Vector2 position) { this.position = position; }
	
	public void setGraphicsComponent(GraphicsComponent graphicsComponent) { this.graphicsComponent = graphicsComponent; }
	public void setCollisionComponent(CollisionComponent collisionComponent) { this.collisionComponent = collisionComponent; }
	
	public abstract void update(double time);
}