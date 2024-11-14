package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Wall extends GameObject {
    private ArrayList<Point> WallVertices;
    private Point bottomRightPoint;
    private Point bottomLeftPoint;
    private ArrayList<Line> lines;
    private Point topRightPoint;
    private Point topLeftPoint;
    private Rectangle hitBox;
    private int height;
    private int width;
    private Point a;

    public Wall(Point a, int height, int width, ImageIcon texture) {
        super(texture);
        this.height = height;
        this.width = width;
        topLeftPoint = a;
        topRightPoint = new Point(a.x + width, a.y) ;
        bottomLeftPoint = new Point(a.x, a.y+height);
        bottomRightPoint = new Point(a.x+width, a.y+height);
        lines = new ArrayList<>();
        lines.add(new Line(topLeftPoint, topRightPoint));
        lines.add(new Line(bottomLeftPoint, bottomRightPoint));
        lines.add(new Line(bottomLeftPoint, topLeftPoint));
        lines.add(new Line(bottomRightPoint, topRightPoint));
        hitBox = new Rectangle(topLeftPoint.x, topLeftPoint.y, width, height);

    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void updatePosition(Point newPos) { // -> ESTE METODO ES PARA ACTUALIZAR EL MURO DEL JUGADOR
        Point topLeftPoint = newPos;
        Point topRightPoint = new Point(newPos.x + width, newPos.y);
        Point bottomLeftPoint = new Point(newPos.x, newPos.y + height);
        Point bottomRightPoint = new Point(newPos.x + width, newPos.y + height);

        lines.clear();
        lines.add(new Line(topLeftPoint, topRightPoint));
        lines.add(new Line(bottomLeftPoint, bottomRightPoint));
        lines.add(new Line(bottomLeftPoint, topLeftPoint));
        lines.add(new Line(bottomRightPoint, topRightPoint));
    }

    public boolean containsPoint(Point point) {
        int x = point.x;
        int y = point.y;

        int left = topLeftPoint.x;
        int right = topLeftPoint.x + width;
        int top = topLeftPoint.y;
        int bottom = topLeftPoint.y + height;


        return x >= left && x <= right && y >= top && y <= bottom;

    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
