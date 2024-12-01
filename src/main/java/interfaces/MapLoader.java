package main.java.interfaces;

import main.java.logic.*;

import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
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
	private static MapLoader instance;

	private Player player;
	
	private Set<Integer> downKeys;
	private ArrayList<GameNode> nodes;
	
	private ArrayList<RaycastComponent> raycastComponents;
	private ArrayList<GraphicsComponent> graphicsComponents;
	private ArrayList<CollisionComponent> collisionComponents;

	private ArrayList<Collectable> collectables;
	private ArrayList<Enemy> enemies;

	private ArrayList<GameNode> nodesToRemove;
	private ArrayList<GameNode> nodesToAdd;

	private ArrayList<SimpleEntry<GameNode, Integer>> auxNodes;

	private final double tileW = 80;
	private final double tileH = 80;
	
	private final double windowSizeX = Window.getInstance().getContentPane().getSize().getWidth();
	private final double windowSizeY = Window.getInstance().getContentPane().getSize().getHeight();
	
	private Image textureBackground = new ImageIcon(this.getClass().getClassLoader().getResource("background.png")).getImage();
	
	private final Image textureTile = new ImageIcon(this.getClass().getClassLoader().getResource("tile.png")).getImage();
	private final Image textureWall = new ImageIcon(this.getClass().getClassLoader().getResource("wall.png")).getImage();
	private final Image textureEnemy = new ImageIcon(this.getClass().getClassLoader().getResource("enemy.png")).getImage();
	private final Image texturePlayer = new ImageIcon(this.getClass().getClassLoader().getResource("player.png")).getImage();
	private final Image textureBullet = new ImageIcon(this.getClass().getClassLoader().getResource("bullet.png")).getImage();

	private final ArrayList<Image> texturesCollectables = new ArrayList<>();

	private static final double UPDATE_TIME = 0.016;

	private long lastShotTime = 0;
	public MapLoader(String file)
	{
		this.setBackground(Color.BLACK);

		for(int i = 1;i <= 5;i++)
		{
			texturesCollectables.add(
				new ImageIcon(this.getClass().getClassLoader().getResource("collectable" + i + ".png")).getImage()
			);
		}
		
		this.downKeys = new HashSet<>();
		this.nodes = new ArrayList<>();
		
		this.raycastComponents = new ArrayList<>();
		this.graphicsComponents = new ArrayList<>();
		this.collisionComponents = new ArrayList<>();

		this.collectables = new ArrayList<>();
		this.enemies = new ArrayList<>();

		this.nodesToRemove = new ArrayList<>();
		this.nodesToAdd = new ArrayList<>();
		
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
		
		auxNodes = new ArrayList<>();
		
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
							node = new Player(position, 5, this);
							
							graphicsComponent = new GraphicsComponent(node, texturePlayer, new Vector2(32, 32));
							collisionComponent = new CollisionComponent(node, new Vector2(32, 32));
							
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
							node = new Enemy(position, movementZone, 5, this);
							enemies.add((Enemy) node);

							raycastComponent = new RaycastComponent(node, position);
							graphicsComponent = new GraphicsComponent(node, textureEnemy, new Vector2(32, 32));
							collisionComponent = new CollisionComponent(node, new Vector2(32, 32));
						} break;

						case '4':
						{
							type = 4;

							node = new Collectable(position, this);

							int randIndex = (int) (Math.random() * 5);
							graphicsComponent = new GraphicsComponent(node, texturesCollectables.get(randIndex), new Vector2(32, 32));
							collisionComponent = new CollisionComponent(node, new Vector2(32, 32));
							
							collectables.add((Collectable) node);
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
		super.paintComponent(renderer);
		
		{
			if(this.downKeys.contains(KeyEvent.VK_A))
				this.player.addToDirection(-1.25);
			
			if(this.downKeys.contains(KeyEvent.VK_D))
				this.player.addToDirection(+1.25);
			
			if(this.downKeys.contains(KeyEvent.VK_W) || downKeys.contains(KeyEvent.VK_S))
			{
				if(this.downKeys.contains(KeyEvent.VK_W))
					this.player.setVelocity(+100);
				
				if(this.downKeys.contains(KeyEvent.VK_S))
					this.player.setVelocity(-100);
			}
			
			else
				this.player.setVelocity(0);

			// -------> BALAZO
			if(this.downKeys.contains(KeyEvent.VK_SPACE))
			{
				shootBullet(this.player);
			}

			// -------> DASHEO
			if(this.downKeys.contains(KeyEvent.VK_SHIFT))
			{
				this.player.dash();
			}

		}
		
		renderer.drawImage(textureBackground, 0, 0, null);
		
		for(GameNode node : nodes)
			node.update(UPDATE_TIME);
		
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
			this.render(renderer, component);
		
		ArrayList<Line> intersectionLines = new ArrayList<>();
		
		for(CollisionComponent component : collisionComponents)
		{
			if(!(component.getOwner() instanceof Player) && !(component.getOwner() instanceof Wall))
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



		// -> BARRAS DE VIDA
		renderer.setColor(Color.GRAY);
		renderer.fillRect(25, 25, 100, 25);
		renderer.setColor(Color.GREEN);
		renderer.fillRect(25, 25, (int) (this.player.getHealth() * (100 / this.player.getMaxHealth())), 25);

		for(Enemy enemy : enemies)
		{
			Vector2 enemyPosition = this.center(enemy.getPosition());

			int barWidth = 25;
			int barHeight = 5;
			int offsetY = 20;

			int healthPercentage = (int) ((enemy.getHealth() / enemy.getMaxHealth()) * barWidth);

			renderer.setColor(Color.GRAY);
			renderer.fillRect((int) enemyPosition.x - (barWidth / 2), (int) enemyPosition.y + offsetY, barWidth, barHeight);

			renderer.setColor(Color.RED);
			renderer.fillRect((int) enemyPosition.x - (barWidth / 2), (int) enemyPosition.y + offsetY, healthPercentage, barHeight);

		}

		// -> DISPAROS DE ENEMIGO
		for(Enemy enemy : enemies)
		{
			if(enemy.canSeePlayer(this.player))
			{
				shootBullet(enemy);
				//enemy.setVelocity(500);
			} else {
				//enemy.setVelocity(100);
			}
		}

		// -> OBJETIVOS DE JUEGO
		checkStatus();
		
		/* DEBUGGING */
		
//		for(GameNode node : nodes)
//		{
//			CollisionComponent collisionComponent = node.getCollisionComponent();
//
//			if(collisionComponent == null)
//				continue;
//
//			renderer.setColor(Color.CYAN);
//			this.renderBox(renderer, node.getPosition(), collisionComponent.getDims());
//		}
//
//		for(GameNode node : nodes)
//		{
//			if(!(node instanceof Enemy))
//				continue;
//
//			Enemy enemy = (Enemy) node;
//
//			MovementZone movementZone = enemy.getMovementZone();
//
//			if(movementZone instanceof BoxMovementZone)
//			{
//				BoxMovementZone zone = (BoxMovementZone) movementZone;
//
//				renderer.setColor(Color.GREEN);
//				this.renderBox(renderer, zone.getPosition(), zone.getDims());
//			}
//
//			if(movementZone instanceof LineMovementZone)
//			{
//				LineMovementZone zone = (LineMovementZone) movementZone;
//
//				renderer.setColor(Color.GREEN);
//				this.renderLine(renderer, zone.getPointA(), zone.getPointB());
//			}
//		}



		// -> AGREGAR/REMOVER NODOS
		processPendingNodes();
	}
	
	public void setDownKeys(Set<Integer> downKeys)
	{
		this.downKeys = downKeys;
	}

	public void processPendingNodes()
	{
		for (GameNode node : nodesToAdd) {

			if (node.getRaycastComponent() != null)
				this.raycastComponents.add(node.getRaycastComponent());
			if (node.getGraphicsComponent() != null)
				this.graphicsComponents.add(node.getGraphicsComponent());
			if (node.getCollisionComponent() != null)
				this.collisionComponents.add(node.getCollisionComponent());
			nodes.add(node);

		}
		nodesToAdd.clear();

		for (GameNode node : nodesToRemove) {

			if (node.getRaycastComponent() != null)
				this.raycastComponents.remove(node.getRaycastComponent());
			if (node.getGraphicsComponent() != null)
				this.graphicsComponents.remove(node.getGraphicsComponent());
			if (node.getCollisionComponent() != null)
				this.collisionComponents.remove(node.getCollisionComponent());
			nodes.remove(node);

		}
		nodesToRemove.clear();
	}

	public void removeNode(GameNode node)
	{
		nodesToRemove.add(node);

		if(node instanceof Collectable)
		{
			collectables.remove(node);
		}

		if(node instanceof Enemy)
		{
			enemies.remove(node);
		}

	}

	public void addNode(GameNode node)
	{
		nodesToAdd.add(node);
	}

	// ------> OBJETIVOS DEL JUEGO
	public void checkStatus()
	{
		if(this.player.getHealth() <= 0)
		{
			PrincipalPanel.getInstance().showPanel("LevelLost");
			
			return;
		}
		
		if(collectables.isEmpty())
		{
			PrincipalPanel.getInstance().showPanel("LevelCompleted");
			
			return;
		}

		if(enemies.isEmpty())
		{
			PrincipalPanel.getInstance().showPanel("LevelCompleted");
		}
	}

	public void shootBullet(GameNode shooterNode)
	{
		long currentTime = System.currentTimeMillis();

		if(shooterNode instanceof Enemy)
		{
			Enemy enemy = (Enemy) shooterNode;
			if(currentTime - enemy.getLastShotTime() < 1500) return;

			enemy.setLastShotTime(currentTime);
		} else {

			if(currentTime - lastShotTime < 1000) return;

			lastShotTime = currentTime;
		}

		double direction = Math.toRadians(shooterNode.getDirection());
		Vector2 offset = new Vector2(Math.cos(direction), Math.sin(direction));

		CollisionComponent shooterCollision = shooterNode.getCollisionComponent();
		double offsetMagnitude = Math.max(shooterCollision.getDims().x, shooterCollision.getDims().y) / 2 + 21;
		offset.scale(offsetMagnitude);

		Vector2 bulletPosition = new Vector2(shooterNode.getPosition());
		bulletPosition.add(offset);

		Bullet bullet = new Bullet(bulletPosition, shooterNode.getDirection(), this);

		GraphicsComponent graphicsComponent = new GraphicsComponent(bullet, textureBullet, new Vector2(20,20));
		CollisionComponent collisionComponent = new CollisionComponent(bullet, new Vector2(20,20));

		bullet.setGraphicsComponent(graphicsComponent);
		bullet.setCollisionComponent(collisionComponent);

		nodesToAdd.add(bullet);
//		this.nodes.add(bullet);
//		this.graphicsComponents.add(bullet.getGraphicsComponent());
//		this.collisionComponents.add(bullet.getCollisionComponent());

	}


	private void render(Graphics renderer, GraphicsComponent graphicsComponent)
	{
		double direction = graphicsComponent.getOwner().getDirection();
		Vector2 position = this.center(graphicsComponent.getOwner().getPosition());
		
		double sizeX = graphicsComponent.getDims().x;
		double sizeY = graphicsComponent.getDims().y;
		
		int posX = (int) (position.x - (sizeX / 2));
		int posY = (int) (position.y - (sizeY / 2));
		
		Graphics2D renderer2 = (Graphics2D) renderer.create();

		if(graphicsComponent.getOwner() instanceof TrailSegment)
		{
			TrailSegment trailSegment = (TrailSegment) graphicsComponent.getOwner();
			float alpha = (float) trailSegment.getOpacity();
			renderer2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
		
		renderer2.rotate(Math.toRadians(direction), posX + (sizeX / 2), posY + (sizeY / 2));
		
		renderer2.drawImage(graphicsComponent.getTexture(), posX, posY, (int) sizeX, (int) sizeY, null);

		renderer2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		renderer2.dispose();
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