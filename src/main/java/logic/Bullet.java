package main.java.logic;

import main.java.interfaces.MapLoader;

public class Bullet extends GameNode{

    private double velocity;

    private MapLoader mapLoader;
    public Bullet(Vector2 position, double direction, MapLoader mapLoader)
    {
        super(position);

        this.velocity = 200;
        this.direction = direction;

        this.mapLoader = mapLoader;

    }


    @Override
    public void update(double time) {

        this.position.x += Math.cos(Math.toRadians(this.direction)) * velocity * time;
        this.position.y += Math.sin(Math.toRadians(this.direction)) * velocity * time;

    }

    @Override
    public void manageCollision(GameNode node) {
        mapLoader.removeNode(this);
    }

    @Override
    public void manageIntersection(GameNode node) {

    }
}
