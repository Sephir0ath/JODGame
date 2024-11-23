package Interface;

import logic.*;

import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class MapLoader extends JPanel
{
	private Player player;
	
	private ArrayList<GameNode> nodes;
	private ArrayList<Enemy> enemies;
	
	private double tileW;
	private double tileH;
	
	private Vector2 spawn;
	
	private final ImageIcon textureTile = new ImageIcon(this.getClass().getClassLoader().getResource("tile.png"));
	private final ImageIcon textureWall = new ImageIcon(this.getClass().getClassLoader().getResource("wall.png"));
	private final ImageIcon textureEnemy = new ImageIcon(this.getClass().getClassLoader().getResource("enemy.png"));
	private final ImageIcon texturePlayer = new ImageIcon(this.getClass().getClassLoader().getResource("player.png"));
	
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
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			for(int r = 0; r < rowCount; r++)
			{
				String line = reader.readLine();
				
				for(int c = 0; c < colCount; c++)
				{
					Vector2 position = new Vector2();
					{
						position.x = (double) ((c * this.tileW) + (this.tileW / 2));
						position.y = (double) ((r * this.tileH) + (this.tileH / 2));
					}
					
					GameNode node = null;
					
					GraphicsComponent graphicsComponent = null;
					CollisionComponent collisionComponent = null;
					
					switch(line.charAt(c))
					{
						case '0':
						{
							node = new Tile(position);
							
							graphicsComponent = new GraphicsComponent(node, textureTile);
						} break;
						
						case '1':
						{
							node = new Wall(position);
							
							graphicsComponent = new GraphicsComponent(node, textureWall);
							collisionComponent = new CollisionComponent(node, new Vector2(this.tileW, this.tileH));
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
							
							node = new Enemy(position, movementZone);
							
							graphicsComponent = new GraphicsComponent(node, textureEnemy);
							collisionComponent = new CollisionComponent(node, new Vector2(25, 25));
						} break;
					}
					
					if(node == null)
						continue;
					
					node.setGraphicsComponent(graphicsComponent);
					node.setCollisionComponent(collisionComponent);
					
					this.nodes.add(node);
					
					if(node instanceof Enemy)
						this.enemies.add((Enemy) node);
				}
			}
		}
		
		catch(IOException exception)
		{
			exception.printStackTrace();
			
			throw new RuntimeException(exception);
		}
		
		this.player = player;
		{
			GraphicsComponent graphicsComponent = new GraphicsComponent(this.player, texturePlayer);
			CollisionComponent collisionComponent = new CollisionComponent(this.player, new Vector2(25, 25));
			
			this.player.setGraphicsComponent(graphicsComponent);
			this.player.setCollisionComponent(collisionComponent);
		}
		
		nodes.add(this.player);
		
		player.setPosition(spawn);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Renderizar
		for(GameNode node : nodes)
		{
			Vector2 position = node.getPosition();
			
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
					nodeA.manageCollision(nodeB);
					nodeB.manageCollision(nodeA);
					
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