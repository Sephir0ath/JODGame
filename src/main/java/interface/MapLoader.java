package Interface;

import logic.*;

import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.KeyEvent;

public class MapLoader extends JPanel
{
	private Player player;
	
	private Set<Integer> downKeys;
	private ArrayList<GameNode> nodes;
	
	private ArrayList<RaycastComponent> raycastComponents;
	private ArrayList<GraphicsComponent> graphicsComponents;
	private ArrayList<CollisionComponent> collisionComponents;
	
	private final double tileW = 80;
	private final double tileH = 80;
	
	private final double windowSizeX = Window.getInstance().getContentPane().getSize().getWidth();
	private final double windowSizeY = Window.getInstance().getContentPane().getSize().getHeight();
	
	private final ImageIcon textureTile = new ImageIcon(this.getClass().getClassLoader().getResource("tile.png"));
	private final ImageIcon textureWall = new ImageIcon(this.getClass().getClassLoader().getResource("wall.png"));
	private final ImageIcon textureEnemy = new ImageIcon(this.getClass().getClassLoader().getResource("enemy.png"));
	private final ImageIcon texturePlayer = new ImageIcon(this.getClass().getClassLoader().getResource("player.png"));
	
	private static final double UPDATE_TIME = 0.016;
	
	public MapLoader(String file)
	{
		this.setBackground(Color.BLACK);
		
		this.downKeys = new HashSet<>();
		
		this.nodes = new ArrayList<>();
		
		this.raycastComponents = new ArrayList<>();
		this.graphicsComponents = new ArrayList<>();
		this.collisionComponents = new ArrayList<>();
		
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
		
		ArrayList<SimpleEntry<GameNode, Integer>> auxNodes = new ArrayList<>();
		
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
					
					{
						GameNode tile = new Tile(new Vector2(position));
						tile.setGraphicsComponent(new GraphicsComponent(tile, textureTile, new Vector2(this.tileW, this.tileH)));
						
						auxNodes.add(new SimpleEntry(tile, 0));
					}
					
					Integer type = 0;
					GameNode node = null;
					
					RaycastComponent raycastComponent = null;
					GraphicsComponent graphicsComponent = null;
					CollisionComponent collisionComponent = null;
					
					switch(line.charAt(c))
					{
						case '1':
						{
							type = 1;
							node = new Wall(position);
							
							graphicsComponent = new GraphicsComponent(node, textureWall, new Vector2(this.tileW, this.tileH));
							collisionComponent = new CollisionComponent(node, new Vector2(this.tileW, this.tileH));
						} break;
						
						case '2':
						{
							if(this.player != null)
								continue;
							
							type = 3;
							node = new Player(position);
							
							raycastComponent = new RaycastComponent(node, position);
							graphicsComponent = new GraphicsComponent(node, texturePlayer, new Vector2(25, 25));
							collisionComponent = new CollisionComponent(node, new Vector2(25, 25));
							
							this.player = (Player) node;
						} break;
						
						case '3':
						{
							MovementZone movementZone = null;
							
							if(Math.random() < 0.5)
								movementZone = new BoxMovementZone(new Vector2(position), new Vector2(100, 100));
							
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
							
							type = 2;
							node = new Enemy(position, movementZone);
							
							raycastComponent = new RaycastComponent(node, position);
							graphicsComponent = new GraphicsComponent(node, textureEnemy, new Vector2(25, 25));
							collisionComponent = new CollisionComponent(node, new Vector2(25, 25));
						} break;
					}
					
					if(node == null)
						continue;
					
					node.setRaycastComponent(raycastComponent);
					node.setGraphicsComponent(graphicsComponent);
					node.setCollisionComponent(collisionComponent);
					
					auxNodes.add(new SimpleEntry<>(node, type));
				}
			}
		}
		
		catch(IOException exception)
		{
			exception.printStackTrace();
			
			throw new RuntimeException(exception);
		}
		
		Collections.sort(auxNodes, Comparator.comparing(pair -> pair.getValue()));
		
		for(SimpleEntry entry : auxNodes)
		{
			GameNode node = (GameNode) entry.getKey();
			
			RaycastComponent raycastComponent = node.getRaycastComponent();
			GraphicsComponent graphicsComponent = node.getGraphicsComponent();
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			this.nodes.add(node);
			
			if(raycastComponent != null)
				this.raycastComponents.add(raycastComponent);
			
			if(graphicsComponent != null)
				this.graphicsComponents.add(graphicsComponent);
			
			if(collisionComponent != null)
				this.collisionComponents.add(collisionComponent);
		}
	}
	
	@Override
	protected void paintComponent(Graphics renderer)
	{
		if(this.downKeys.contains(KeyEvent.VK_A))
			this.player.addToDirection(-1);
		
		if(this.downKeys.contains(KeyEvent.VK_D))
			this.player.addToDirection(+1);
		
		if(this.downKeys.contains(KeyEvent.VK_W) || downKeys.contains(KeyEvent.VK_S))
		{
			if(this.downKeys.contains(KeyEvent.VK_W))
				this.player.setVelocity(+100);
			
			if(this.downKeys.contains(KeyEvent.VK_S))
				this.player.setVelocity(-100);
		}
		
		else
			this.player.setVelocity(0);
		
		super.paintComponent(renderer);
		
		for(CollisionComponent componentA : collisionComponents)
		{
			for(CollisionComponent componentB : collisionComponents)
			{
				if(componentA == componentB)
					continue;
				
				if(CollisionComponent.areColliding(componentA, componentB))
				{
					GameNode nodeA = componentA.getOwner();
					GameNode nodeB = componentB.getOwner();
					
					nodeA.manageCollision(nodeB);
					nodeB.manageCollision(nodeA);
				}
			}
		}
		
		for(RaycastComponent raycaster : this.raycastComponents)
		{
			for(CollisionComponent component : this.collisionComponents)
			{
				GameNode nodeA = raycaster.getOwner();
				GameNode nodeB = component.getOwner();
				
				if(raycaster.intersects(component))
					nodeA.manageIntersection(nodeB);
			}
		}
		
		for(GraphicsComponent component : this.graphicsComponents)
		{
			this.render(renderer, component);
		}
		
		for(GameNode node : nodes)
			node.update(UPDATE_TIME);
		
		/* DEBUGGING */
		
		ArrayList<Line> intersectionLines = new ArrayList<>();
		
		for(CollisionComponent component : collisionComponents)
		{
			if(component.getOwner() instanceof Player)
				continue;
			
			if(component.getOwner() instanceof Enemy)
				continue;
			
			intersectionLines.addAll(component.getOutline());
		}
		
		for(RaycastComponent raycaster : this.raycastComponents)
		{
			for(Line segment : raycaster.getIntersections(intersectionLines))
			{
				renderer.setColor(Color.RED);
				this.renderLine(renderer, segment.getPointA(), segment.getPointB());
			}
		}
		
		for(GameNode node : nodes)
		{
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			if(collisionComponent == null)
				continue;
			
			renderer.setColor(Color.CYAN);
			this.renderBox(renderer, node.getPosition(), collisionComponent.getDims());
		}
		
		for(GameNode node : nodes)
		{
			if(!(node instanceof Enemy))
				continue;
			
			Enemy enemy = (Enemy) node;
			
			MovementZone movementZone = enemy.getMovementZone();
			
			if(movementZone instanceof BoxMovementZone)
			{
				BoxMovementZone zone = (BoxMovementZone) movementZone;
				
				renderer.setColor(Color.GREEN);
				this.renderBox(renderer, zone.position, zone.dims);
			}
			
			if(movementZone instanceof LineMovementZone)
			{
				LineMovementZone zone = (LineMovementZone) movementZone;
				
				renderer.setColor(Color.GREEN);
				this.renderLine(renderer, zone.pointA, zone.pointB);
			}
		}
	}
	
	public void setDownKeys(Set<Integer> downKeys)
	{
		this.downKeys = downKeys;
	}
	
	private void render(Graphics renderer, GraphicsComponent graphicsComponent)
	{
		Vector2 position = this.center(graphicsComponent.getOwner().getPosition());
		
		double sizeX = graphicsComponent.getDims().x;
		double sizeY = graphicsComponent.getDims().y;
		
		int posX = (int) (position.x - (sizeX / 2));
		int posY = (int) (position.y - (sizeY / 2));
		
		renderer.drawImage(graphicsComponent.getTexture().getImage(), posX, posY, (int) sizeX, (int) sizeY, null);
	}
	
	private void renderBox(Graphics renderer, Vector2 position, Vector2 size)
	{
		Vector2 newPosition = this.center(position);
		
		double sizeX = size.x;
		double sizeY = size.y;
		
		int posX = (int) (newPosition.x - (sizeX / 2));
		int posY = (int) (newPosition.y - (sizeY / 2));
		
		renderer.drawRect(posX, posY, (int) sizeX, (int) sizeY);
	}
	
	private void renderLine(Graphics renderer, Vector2 pointA, Vector2 pointB)
	{
		Vector2 newPointA = this.center(pointA);
		Vector2 newPointB = this.center(pointB);
		
		renderer.drawLine((int) newPointA.x, (int) newPointA.y, (int) newPointB.x, (int) newPointB.y);
	}
	
	private Vector2 center(Vector2 position)
	{
		Vector2 newPosition = new Vector2(position);
		{
			newPosition.x -= this.player.getPosition().x;
			newPosition.y -= this.player.getPosition().y;
			
			newPosition.x += this.windowSizeX / 2;
			newPosition.y += this.windowSizeY / 2;
		}
		
		return newPosition;
	}
}