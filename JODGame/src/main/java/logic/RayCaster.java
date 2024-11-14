package logic;

import logic.Enemies.Enemy;

import java.awt.*;
import java.util.ArrayList;

public class RayCaster {
    private final int MAX_DISTANCE_VIEW = 300;
    private ArrayList<Ray> raysArray;

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
        for (int i = 0; i < 44; i++) {
            raysArray.get(i).setDirection(Math.cos(Math.toRadians(direction+i -22)), Math.sin(Math.toRadians(direction+i -22)));
        }

    }

    public ArrayList<Point> lookForObstacles(ArrayList<Wall> walls, Wall playerWall, boolean ignorePlayerWall, Enemy enemy) {
        double[] endPoint = new double[2];
        ArrayList<Point> endPoints = new ArrayList<>();
        boolean playerWallDetected = false; // -> Para enemigos

        for (Ray ray : raysArray) {
            float minDistance = 10000;
            float[] closestIntersectPoint = new float[2];


            if (!ignorePlayerWall) {
                for (Line playerWallLine : playerWall.getLines()) {
                    float[] intersectPoint = ray.cast(playerWallLine);
                    if (intersectPoint != null) {
                        float x = ray.getPos().x - intersectPoint[0];
                        float y = ray.getPos().y - intersectPoint[1];
                        float dist = (float) Math.sqrt(x * x + y * y);



                        if (dist < minDistance) {
                            minDistance = dist;
                            closestIntersectPoint = intersectPoint;
        //                    playerWallDetected = true;
                        }
                    }
                }
            }


            for (Wall wall : walls) {
                for (Line wallLine : wall.getLines()) {
                    float[] intersectPoint = ray.cast(wallLine);
                    if (intersectPoint != null) {
                        float x = ray.getPos().x - intersectPoint[0];
                        float y = ray.getPos().y - intersectPoint[1];
                        float dist = (float) Math.sqrt(x * x + y * y);

                        if (dist < minDistance) {
                            minDistance = dist;
                            closestIntersectPoint = intersectPoint;
        //                    playerWallDetected = false;
                        }
                    }
                }
            }

            // Agrega el punto de intersección más cercano o el final del rayo
            if (minDistance < MAX_DISTANCE_VIEW) {
                endPoint[0] = closestIntersectPoint[0];
                endPoint[1] = closestIntersectPoint[1];
                endPoints.add(new Point((int) endPoint[0], (int) endPoint[1]));
                //if(enemy != null && playerWallDetected) {
                //    ((Enemy) enemy).setDetectingPlayer(true);
                //}

            } else {
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









