package logic;

public class Wall extends GameNode
{
	public Wall(Vector2 position)
	{
		super(position);
	}
	
	@Override
	public void update(double time) {}
	
	@Override
	public void manageCollision(GameNode node) {}
}