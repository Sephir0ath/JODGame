package Interface;

import logic.*;

import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Objects;

public class MapLoader extends JPanel
{
	private Player player;
	
	private ArrayList<GameNode> nodes;
	private ArrayList<Enemy> enemies;
	
	private double tileW;
	private double tileH;
	
	private Vector2 spawn;
	
	private final ImageIcon wallTexture = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("wall.png")));
	private final ImageIcon enemyTexture = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("enemy.png")));
	private final ImageIcon playerTexture = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("player.png")));
	
	public MapLoader(Player player, String file)
	{
		this.setBackground(Color.BLACK);
		
		this.nodes = new ArrayList<>();
		this.enemies = new ArrayList<>();
		
		int rowCount = 0;
		int colCount = 0;
		{
			try(BufferedReader reader = new BufferedReader(new FileReader(file)))
			{
				String line;
				
				while((line = reader.readLine()) != null)
				{
					colCount = line.length();
					rowCount += 1;
				}
			}
			
			catch(IOException exception)
			{
				exception.printStackTrace();
				
				throw new RuntimeException(exception);
			}
		}
		
		this.tileW = Window.getInstance().getWidth() / colCount;
		this.tileH = Window.getInstance().getHeight() / rowCount;
		
		this.player = player;
		{
			GraphicsComponent graphicsComponent = new GraphicsComponent(this.player, playerTexture);
			CollisionComponent collisionComponent = new CollisionComponent(this.player, new Vector2(25, 25));
			
			this.player.setGraphicsComponent(graphicsComponent);
			this.player.setCollisionComponent(collisionComponent);
		}
		
		nodes.add(this.player);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			int r = 0;
			int c = 0;
			
			String line;
			
			while((line = reader.readLine()) != null)
			{
				for(c = 0; c < colCount; c++)
				{
					Vector2 position = new Vector2((double) ((c * this.tileW) + (this.tileW / 2)), (double) ((r * this.tileH) + (this.tileH / 2)));
					
					GameNode newNode = null;
					
					GraphicsComponent graphicsComponent = null;
					CollisionComponent collisionComponent = null;
					
					switch(line.charAt(c))
					{
						case '0':
						{
						} break;
						
						case '1':
						{
							newNode = new Wall(position);
							
							collisionComponent = new CollisionComponent(newNode, new Vector2(this.tileW, this.tileH));
						} break;
						
						case '2':
						{
							this.spawn = position;
						} break;
						
						case '3':
						{
							MovementZone movementZone = null;
							
							if(Math.random() < 0.5)
								movementZone = new BoxMovementZone(100, 100, new Vector2(position));
							
							else
							{
								Vector2 pointA;
								Vector2 pointB;
								
								if(Math.random() < 0.5)
								{
									pointA = new Vector2(position.x, position.y - 50);
									pointB = new Vector2(position.x, position.y + 50);
								}
								
								else
								{
									pointA = new Vector2(position.x - 50, position.y);
									pointB = new Vector2(position.x + 50, position.y);
								}
								
								movementZone = new LineMovementZone(pointA, pointB);
							}
							
							newNode = new Enemy(position, movementZone);
							
							graphicsComponent = new GraphicsComponent(newNode, enemyTexture);
							collisionComponent = new CollisionComponent(newNode, new Vector2(25, 25));
						} break;
					}
					
					if(newNode == null)
						continue;
					
					newNode.setGraphicsComponent(graphicsComponent);
					newNode.setCollisionComponent(collisionComponent);
					
					this.nodes.add(newNode);
					
					if(newNode instanceof Enemy)
						this.enemies.add((Enemy) newNode);
				}
				
				r += 1;
			}
		}
		
		catch(IOException exception)
		{
			exception.printStackTrace();
			
			throw new RuntimeException(exception);
		}
		
		player.setPosition(spawn);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Renderizar
		for(GameNode node : nodes)
		{
			Vector2 position = node.getPosition();
			
			if(node instanceof Wall)
			{
				g.setColor(Color.CYAN);
				g.fillRect((int) (position.x - (this.tileW / 2)), (int) (position.y - (this.tileH / 2)), (int) this.tileW, (int) this.tileH);
				
				continue;
			}
			
			GraphicsComponent graphicsComponent = node.getGraphicsComponent();
			
			if(graphicsComponent == null)
				continue;
			
			double sizeX = graphicsComponent.getTexture().getImage().getWidth(null);
			double sizeY = graphicsComponent.getTexture().getImage().getHeight(null);
			
			g.drawImage(graphicsComponent.getTexture().getImage(), (int) (position.x - (sizeX / 2)), (int) (position.y - (sizeY / 2)), null);
		}
		
		// Colisiones
		for(GameNode nodeA : nodes)
		{
			for(GameNode nodeB : nodes)
			{
				if(nodeA == nodeB)
					continue;
				
				CollisionComponent collisionComponentA = nodeA.getCollisionComponent();
				CollisionComponent collisionComponentB = nodeB.getCollisionComponent();
				
				if((collisionComponentA == null) || (collisionComponentB == null))
					continue;
				
				if(CollisionComponent.areColliding(collisionComponentA, collisionComponentB))
				{
					if(nodeA != this.player)
						continue;
					
					{
						double aux = this.player.getVelocity();
						
						this.player.setVelocity(-aux);
						this.player.update(0.016);
						this.player.setVelocity(aux);
					}
					
					Vector2 positionA = nodeA.getPosition();
					Vector2 positionB = nodeB.getPosition();
					
					g.setColor(Color.WHITE);
					g.fillOval((int) positionA.x, (int) positionA.y, 10, 10);
					g.fillOval((int) positionB.x, (int) positionB.y, 10, 10);
				}
			}
		}
		
		ArrayList<Line> collisionLines = new ArrayList<>();
		
		for(GameNode node : nodes)
		{
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			if((collisionComponent != null) && !(node instanceof Enemy))
				collisionLines.addAll(collisionComponent.getOutline());
		}
		
		// Raycasting
		g.setColor(Color.GREEN);
		{
			ArrayList<Line> segments = player.getRayCaster().getIntersections(collisionLines);
			
			for(Line segment : segments)
			{
				int startX = (int) segment.getPointA().x;
				int startY = (int) segment.getPointA().y;
				
				int finishX = (int) segment.getPointB().x;
				int finishY = (int) segment.getPointB().y;
				
				g.drawLine(startX, startY, finishX, finishY);
			}
		}
		
		g.setColor(Color.RED);
		{
			for(Enemy enemy : enemies)
			{
				ArrayList<Line> segments = enemy.getRayCaster().getIntersections(collisionLines);
				
				for(Line segment : segments)
				{
					int startX = (int) segment.getPointA().x;
					int startY = (int) segment.getPointA().y;
					
					int finishX = (int) segment.getPointB().x;
					int finishY = (int) segment.getPointB().y;
					
					if(this.player.getCollisionComponent().contains(segment.getPointB()))
						enemy.setTarget(new Vector2(this.player.getPosition()));
					
					g.drawLine(startX, startY, finishX, finishY);
				}
			}
		}
		
		for(Enemy enemy : enemies)
			enemy.update(0.016);
		
		// Debugging
		for(GameNode node : nodes)
		{
			Vector2 position = node.getPosition();
			
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			if(collisionComponent == null)
				continue;
			
			double sizeX = collisionComponent.getDims().x;
			double sizeY = collisionComponent.getDims().y;
			
			g.setColor(Color.CYAN);
			g.drawRect((int) (position.x - (sizeX / 2)), (int) (position.y - (sizeY / 2)), (int) sizeX, (int) sizeY);
		}
		
		for(Enemy enemy : enemies)
		{
			if(enemy.movementZone instanceof BoxMovementZone)
			{
				BoxMovementZone zone = (BoxMovementZone) enemy.movementZone;
				
				Vector2 center = zone.center;
				
				double sizeX = zone.sizeX;
				double sizeY = zone.sizeY;
				
				g.setColor(Color.GREEN);
				g.drawRect((int) (center.x - (sizeX / 2)), (int) (center.y - (sizeY / 2)), (int) sizeX, (int) sizeY);
			}
			
			if(enemy.movementZone instanceof LineMovementZone)
			{
				LineMovementZone zone = (LineMovementZone) enemy.movementZone;
				
				int startX = (int) zone.pointA.x;
				int startY = (int) zone.pointA.y;
				
				int finishX = (int) zone.pointB.x;
				int finishY = (int) zone.pointB.y;
				
				g.setColor(Color.GREEN);
				g.drawLine(startX, startY, finishX, finishY);
			}
		}
	}
}