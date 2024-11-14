package logic;

import java.awt.*;

public class Ray {
    private double directionY;
    private double directionX;
    private Point pos;

    public Ray(Point pos, int angle) {
        this.pos = pos;
        this.directionX = Math.cos(angle*Math.PI/180);
        this.directionY = Math.sin(angle*Math.PI/180);;
    }

    public float[] cast(Line linePoints) {
        float[] intersectPoint = new float[2];

        if(linePoints == null) return null;
        double x1 = linePoints.getStartPoint().x, y1 = linePoints.getStartPoint().y, x2 = linePoints.getEndPoint().x, y2 = linePoints.getEndPoint().y;
        double x3 = pos.x, y3 = pos.y, x4 = pos.x + directionX, y4 = pos.y + directionY;

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            return null;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -1 * ((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t >= 0 && t <= 1 && u >= 0) {
            intersectPoint[0] = (int) (x1 + (t * (x2 - x1)));
            intersectPoint[1] = (int) (y1 + (t * (y2 - y1)));
            return intersectPoint;
        }
        else {
            return null;
        }
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getPos() {
        return pos;
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirection(double x, double y) {
        this.directionX = x;
        this.directionY = y;
    }

}
