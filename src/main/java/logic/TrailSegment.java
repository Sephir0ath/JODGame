package main.java.logic;

import main.java.interfaces.MapLoader;

// GameNode "temporal" con solo componente grafico
// se utilizará para crear un rastro de GameNode en desplazamientos
public class TrailSegment extends GameNode{

    private double lifetime;
    private double opacity;
    private double fadeSpeed;




    MapLoader mapLoader;
    public TrailSegment(Vector2 position, double direction, double lifetime, MapLoader mapLoader)
    {
        super(position);
        this.direction = direction;
        this.lifetime = lifetime;
        this.fadeSpeed = 1.0 / lifetime;
        this.opacity = 1;

        this.mapLoader = mapLoader;
    }

    public double getOpacity()
    {
        return opacity;
    }

    @Override
    public void update(double time) {
        lifetime -= time;
        opacity = Math.max(0, opacity - fadeSpeed * time);

        if(lifetime <= 0)
        {
            mapLoader.removeNode(this);
        }
    }

    @Override
    public void manageCollision(GameNode node) {

    }

    @Override
    public void manageIntersection(GameNode node) {

    }
}
