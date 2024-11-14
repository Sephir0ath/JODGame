package logic.Enemies;

import java.awt.*;

public abstract class MovementPattern {
    protected Rectangle patrolZone;
    protected Point startPoint;
    protected Point endPoint;

    public MovementPattern(Point startPoint, Point endPoint, Rectangle patrolZone) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.patrolZone = patrolZone;
    }

    public abstract Point getNextPosition(Point currentPos);

    public Rectangle getPatrolZone() {
        return patrolZone;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
