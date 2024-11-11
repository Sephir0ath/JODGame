package logic;

import java.awt.*;

public class Line {
    private Point startPoint;
    private Point endPoint;
    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public boolean containsPoint(Point point) {
        return false;
    }
}
