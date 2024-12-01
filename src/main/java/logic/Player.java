package main.java.logic;

import main.java.interfaces.MapLoader;
import main.java.interfaces.SoundPlayer;

public class Player extends GameNode
{
	private double health;
	private double maxHealth;
	private double invincibleTimer;

	private boolean isDashing = false;
	private Vector2 dashTarget = null;
	private double dashDistance = 100;
	private double dashDistanceRemaining = 0;
	private double dashSpeed = 250;
	private long dashCooldownTime = 1000;
	private long lastDashTime = 0;

	private double velocity;

	private double trailSpawnTimer = 0;
	private MapLoader mapLoader;
	public Player(Vector2 position, double maxHealth, MapLoader mapLoader)
	{
		super(position);
		
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.invincibleTimer = 2;

		this.mapLoader = mapLoader;
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

	public void dash()
	{
		SoundPlayer dashPlayer = new SoundPlayer();
		if (isDashing || System.currentTimeMillis() - lastDashTime < dashCooldownTime) {
			return;
		}
		dashPlayer.play("src/main/resources/MusicaTP.wav");
		isDashing = true;
		lastDashTime = System.currentTimeMillis();



		double dashX = dashDistance * Math.cos(Math.toRadians(this.direction));
		double dashY = dashDistance * Math.sin(Math.toRadians(this.direction));

		dashTarget = new Vector2(this.position.x + dashX, this.position.y + dashY);
		dashDistanceRemaining = dashDistance;

	}

	public void updateTrail(double time)
	{
		trailSpawnTimer -= time;
		if(trailSpawnTimer <= 0)
		{
			Vector2 trailPosition = new Vector2(this.position.x, this.position.y);
			TrailSegment trailSegment = new TrailSegment(trailPosition, this.direction, 1, this.mapLoader);
			GraphicsComponent graphicsComponent =
					new GraphicsComponent(
							trailSegment, this.graphicsComponent.getTexture(),
							this.graphicsComponent.getDims());

			trailSegment.setGraphicsComponent(graphicsComponent);
			mapLoader.addNode(trailSegment);
			trailSpawnTimer = 0.05;
		}

	}
	
	@Override
	public void update(double time)
	{

		if (this.invincibleTimer > 0) {
			this.invincibleTimer = Math.max(0, invincibleTimer - time);
		}

		if (isDashing)
		{
			updateTrail(time);
			double distanceToTravel = dashSpeed * time;

			if (dashDistanceRemaining <= distanceToTravel)
			{

				this.position.x = dashTarget.x;
				this.position.y = dashTarget.y;
				isDashing = false;

			} else {

				double ratio = distanceToTravel / dashDistanceRemaining;
				this.position.x += ratio * (dashTarget.x - this.position.x);
				this.position.y += ratio * (dashTarget.y - this.position.y);
				dashDistanceRemaining -= distanceToTravel;

			}

		} else {

			this.position.x += (velocity * time) * Math.cos(Math.toRadians(this.direction));
			this.position.y += (velocity * time) * Math.sin(Math.toRadians(this.direction));

		}
	}
	
	@Override
	public void manageCollision(GameNode node)
	{
		if((node instanceof Enemy || node instanceof Bullet) && (invincibleTimer == 0))
		{
			this.health -= 1;
			this.invincibleTimer = 2;
		}

		isDashing = false;
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