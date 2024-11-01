package logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class RayCaster {
    private ArrayList<Ray> raysArray;
    private final int MAX_DISTANCE_VIEW = 300;

    public RayCaster(Point pos) {
        raysArray = new ArrayList<>();

        for (int i = -22; i < 22; i++) {
            raysArray.add(new Ray(pos, i));
        }
    }

    public void updateRaysPos(Point pos){
        for (int i = -22; i < 22; i++) {
            raysArray.get(i+22).setPos(pos);
        }
    }

    public void updateRaysDirection(double direction){
        for (int i = 0; i < 44; i++) { // Assuming there are 44 rays (-22 to +21 degrees)
            raysArray.get(i).setDirection(Math.cos(Math.toRadians(direction+i -22)), Math.sin(Math.toRadians(direction+i -22)));
            //System.out.println(i);
            //System.out.println(raysArray.get(i).getDirectionX());
            //System.out.println(raysArray.get(i).getDirectionY());
        }

        /* for (int i = -22; i < 22; i++) {
            double newX = raysArray.get(i+22).getDirectionX() + Math.cos(direction);//direction;//
            double newY = raysArray.get(i+22).getDirectionY() + Math.sin(direction);//direction;//
            raysArray.get(i+22).setDirection(newX, newY);
        }*/
    }

    public ArrayList<Point> lookWalls(ArrayList<Wall> walls){
        double[] endPoint = new double[2];
        ArrayList<Point> endPoints = new ArrayList<>();
        for (Ray ray : raysArray) {
            float minDistance = 10000;
            float[] closestIntersectPoint = new float[2];
            Wall closestWall;

            for (Wall wall : walls) {
                ArrayList<Line> lines = wall.getLines();;
                for (Line line : lines) {
                    float[] intersectPoint = ray.cast(line);
                    if (intersectPoint != null) {
                        float x = ray.getPos().x - intersectPoint[0];
                        float y = ray.getPos().y - intersectPoint[1];
                        float dist = (float) Math.sqrt(x * x + y * y);

                        if (dist < minDistance) {
                            minDistance = dist;
                            closestIntersectPoint = intersectPoint;
                            closestWall = wall;
                        }


                    }

                }

            }

            if (minDistance < MAX_DISTANCE_VIEW) {
                endPoint[0] = closestIntersectPoint[0];
                endPoint[1] = closestIntersectPoint[1];
                endPoints.add(new Point((int)endPoint[0], (int)endPoint[1]));
            }
            else {
                endPoint[0] = ray.getPos().x + ray.getDirectionX() * MAX_DISTANCE_VIEW;
                endPoint[1] = ray.getPos().y + ray.getDirectionY() * MAX_DISTANCE_VIEW;
                endPoints.add(new Point((int) endPoint[0], (int) endPoint[1]));
            }
        }


        return endPoints;
    }


    public ArrayList<Ray> getRaysArray() {
        return raysArray;
    }

}









