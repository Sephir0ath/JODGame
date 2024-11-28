package main.java.logic;

import main.java.interfaces.MapLoader;

public class Collectable extends GameNode{

    private MapLoader mapLoader;

    public Collectable(Vector2 position, MapLoader mapLoader) {
        super(position);
        this.mapLoader = mapLoader;
    }


    @Override
    public void update(double time) {

    }

    @Override
    public void manageCollision(GameNode node) {
        if(node instanceof Player)
        {
            mapLoader.removeCollectable(this);
        }
    }

    @Override
    public void manageIntersection(GameNode node) {

    }
}
