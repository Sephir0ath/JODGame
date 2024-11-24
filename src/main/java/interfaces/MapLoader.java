package main.java.interfaces;

import main.java.logic.*;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class MapLoader extends JPanel
{
	private Player player;
	
	private ArrayList<GameNode> nodes;
	private ArrayList<Enemy> enemies;
	
	private double tileW = 80;
	private double tileH = 80;
	
	private final double windowSizeX = Window.getInstance().getContentPane().getSize().getWidth();
	private final double windowSizeY = Window.getInstance().getContentPane().getSize().getHeight();
	
	private Vector2 spawn;
	
	private final RotatedIcon textureTile = new RotatedIcon(new ImageIcon(this.getClass().getClassLoader().getResource("tile.png")), 0, true);
	private final RotatedIcon textureWall = new RotatedIcon(new ImageIcon(this.getClass().getClassLoader().getResource("wall.png")), 0, true);
	private final RotatedIcon textureEnemy = new RotatedIcon(new ImageIcon(this.getClass().getClassLoader().getResource("enemyOriginal.png")), 0, true);
	private final RotatedIcon texturePlayer = new RotatedIcon(new ImageIcon(this.getClass().getClassLoader().getResource("playerOriginal.png")), 0, true);

	private Image backgroundImage;

	public MapLoader(Player player, String file)
	{

		try {
			backgroundImage = ImageIO.read(new File("src/main/resources/menu_bg.png"));
		} catch(IOException e) {
			e.printStackTrace();
			this.setBackground(Color.BLACK);
		}
		
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
		
		// this.tileW = Window.getInstance().getContentPane().getSize().getWidth() / colCount;
		// this.tileH = Window.getInstance().getContentPane().getSize().getHeight() / rowCount;
		
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
							
							graphicsComponent = new GraphicsComponent(node, textureTile, new Vector2(this.tileW, this.tileH));
						} break;
						
						case '1':
						{
							node = new Wall(position);
							
							graphicsComponent = new GraphicsComponent(node, textureWall, new Vector2(this.tileW, this.tileH));
							collisionComponent = new CollisionComponent(node, new Vector2(this.tileW, this.tileH));
						} break;
						
						case '2':
						{
							this.spawn = position;
							
							node = new Tile(new Vector2(position));
							
							graphicsComponent = new GraphicsComponent(node, textureTile, new Vector2(this.tileW, this.tileH));
						} break;
						
						case '3':
						{
							{
								GameNode auxNode = new Tile(new Vector2(position));
								
								graphicsComponent = new GraphicsComponent(auxNode, textureTile, new Vector2(this.tileW, this.tileH));
								
								auxNode.setGraphicsComponent(graphicsComponent);
								
								this.nodes.add(auxNode);
							}
							
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
							
							node = new Enemy(position, movementZone);
							
							graphicsComponent = new GraphicsComponent(node, textureEnemy, new Vector2(25, 25));
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
			GraphicsComponent graphicsComponent = new GraphicsComponent(this.player, texturePlayer, new Vector2(25, 25));
			CollisionComponent collisionComponent = new CollisionComponent(this.player, new Vector2(25, 25));
			
			this.player.setGraphicsComponent(graphicsComponent);
			this.player.setCollisionComponent(collisionComponent);
		}
		
		nodes.add(this.player);
		
		this.player.setPosition(spawn);
		
		System.out.println(spawn.x + " " + spawn.y);
	}
	
	@Override
	protected void paintComponent(Graphics renderer) {
		super.paintComponent(renderer);

		/* background */
		if(backgroundImage != null) {
			renderer.drawImage(backgroundImage, 0, 0, null);
		}
		
		/* colisiones */
		
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
				}
			}
		}
		
		/* renderizar */
		
		for(GameNode node : nodes)
		{
			GraphicsComponent graphicsComponent = node.getGraphicsComponent();
			
			if(graphicsComponent == null)
				continue;

			if(node instanceof Player)
			{
				double angle = player.getDirection();
				graphicsComponent.rotateSprite(angle);
				this.renderRotated(renderer, graphicsComponent);
			} else if(node instanceof Enemy enemy) {

                double angle = enemy.getDirection();
				graphicsComponent.rotateSprite(angle);
				this.renderRotated(renderer, graphicsComponent);

			} else {
				this.render(renderer, graphicsComponent);
			}

		}

		
		/* raycasting */
		
		ArrayList<Line> collisionLines = new ArrayList<>();
		
		for(GameNode node : nodes)
		{
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			if((collisionComponent != null) && !(node instanceof Enemy))
				collisionLines.addAll(collisionComponent.getOutline());
		}
		
		renderer.setColor(Color.GREEN);
		{
//			ArrayList<Line> segments = player.getRayCaster().getIntersections(collisionLines);
//
//			for(Line segment : segments)
//				this.renderLine(renderer, segment.getPointA(), segment.getPointB());
		}
		
		renderer.setColor(Color.RED);
		{
			for(Enemy enemy : enemies)
			{
				ArrayList<Line> segments = enemy.getRayCaster().getIntersections(collisionLines);
				
				for(Line segment : segments)
				{
					if(this.player.getCollisionComponent().contains(segment.getPointB()))
						enemy.setTarget(new Vector2(this.player.getPosition()));
					
					this.renderLine(renderer, segment.getPointA(), segment.getPointB());
				}
			}
		}
		
		/* actualizar */
		
		for(Enemy enemy : enemies)
			enemy.update(0.016);
		
		/* debugging */
		
		for(GameNode node : nodes)
		{
			CollisionComponent collisionComponent = node.getCollisionComponent();
			
			if(collisionComponent == null)
				continue;
			
			renderer.setColor(Color.CYAN);
			this.renderBox(renderer, node.getPosition(), collisionComponent.getDims());
		}
		
		for(Enemy enemy : enemies)
		{
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
	
	private void render(Graphics renderer, GraphicsComponent graphicsComponent)
	{
		Vector2 position = this.center(graphicsComponent.getOwner().getPosition());
		
		double sizeX = graphicsComponent.getDims().x;
		double sizeY = graphicsComponent.getDims().y;
		
		int posX = (int) (position.x - (sizeX / 2));
		int posY = (int) (position.y - (sizeY / 2));

		renderer.drawImage(graphicsComponent.getTexture(), posX, posY, (int) sizeX, (int) sizeY, null);
	}

	private void renderRotated(Graphics renderer, GraphicsComponent graphicsComponent)
	{
		Vector2 position = this.center(graphicsComponent.getOwner().getPosition());
		double sizeX = graphicsComponent.getDims().x;
		double sizeY = graphicsComponent.getDims().y;

		int posX = (int) (position.x - (sizeX / 2));
		int posY = (int) (position.y - (sizeY / 2));

		RotatedIcon rotatedIcon = graphicsComponent.getRotatedIcon();
		rotatedIcon.paintIcon(null, renderer, posX, posY);


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