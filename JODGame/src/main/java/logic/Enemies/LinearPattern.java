package logic.Enemies;

import java.awt.*;


public class LinearPattern extends MovementPattern {
    private Point startPoint;
    private Point endPoint;
    private Rectangle patrolZone;
    private boolean movingToEnd = true;


    public LinearPattern(Point startPoint, Point endPoint, Rectangle patrolZone) {
        super(startPoint, endPoint, patrolZone);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.patrolZone = patrolZone;
    }


    @Override
    public Point getNextPosition(Point currentPos) {

        if(movingToEnd) {
            return endPoint;
        } else {
            return startPoint;
        }

    }

    public void changeDirection() {
        movingToEnd = !movingToEnd;
    }


}
