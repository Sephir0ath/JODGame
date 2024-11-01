package logic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wall extends GameObject {
    private ArrayList<Point> WallVertices;
    private ArrayList<Line> Lines;
    private Point a;
    public Wall(Point a, int height, int width, ImageIcon texture) {
        super(texture);
        Point topLeftPoint = a, topRightPoint = new Point(a.x + width, a.y), bottomLeftPoint = new Point(a.x, a.y+height), bottomRightPoint = new Point(a.x+width, a.y+height);
        Lines = new ArrayList<>();
        Lines.add(new Line(topLeftPoint, topRightPoint));
        Lines.add(new Line(bottomLeftPoint, bottomRightPoint));
        Lines.add(new Line(bottomLeftPoint, topLeftPoint));
        Lines.add(new Line(bottomRightPoint, topRightPoint));

    }

    public ArrayList<Line> getLines() {
        return Lines;
    }
}
