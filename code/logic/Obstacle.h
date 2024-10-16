#ifndef OBSTACLE_H
#define OBSTACLE_H

#include <iostream>
#include <vector>

struct line{

    std::vector<float> startPoint;
    std::vector<float> endPoint;

};

class Obstacle{
    private:
        
        std::vector<line> lines;
        line topLine, bottomLine, leftLine, rightLine;

    public:

        int obstacleSize;
        std::vector<float> topLeftPoint, topRightPoint, bottomRightPoint, bottomLeftPoint; // -> Vértices del obstaculo a partir de los cuales se forman las líneas
        Obstacle(std::vector<float> topLeftPoint, int obstacleSize); // -> A partir de punto-superior-izquierdo se calculan los demás
        std::vector<line> getLines();


};













#endif

