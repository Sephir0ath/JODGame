#include <vector>


#include "Obstacle.h"



Obstacle::Obstacle(std::vector<float> topLeftPoint, int obstacleSize) { // -> A partir de punto-superior-izquierdo se calculan los demás
    this->topLeftPoint = topLeftPoint;
    this->obstacleSize = obstacleSize;

    // Punto superior derecho a partir de punto-superior-izquierdo
    float x2 = this->topLeftPoint[0] + this->obstacleSize;
    float y2 = this->topLeftPoint[1];
    topRightPoint.push_back(x2);
    topRightPoint.push_back(y2);

    // Punto inferior derecho a partir de punto-superior-izquierdo
    float x3 = this->topLeftPoint[0] + this->obstacleSize;
    float y3 = this->topLeftPoint[1] + this->obstacleSize;
    bottomRightPoint.push_back(x3);
    bottomRightPoint.push_back(y3);

    // Punto inferior izquierdo a partir de punto-superior-izquierdo
    float x4 = this->topLeftPoint[0];
    float y4 = this->topLeftPoint[1] + this->obstacleSize;
    bottomLeftPoint.push_back(x4);
    bottomLeftPoint.push_back(y4);

    /* Creación de las líneas del obstáculo a partir de sus vértices */

    // IMPORTANTE: 
    // lines[0] = linea superior - lines[1] = linea derecha - lines[2] = linea inferior - lines[3] = linea izquierda

    topLine.startPoint = topLeftPoint;
    topLine.endPoint = topRightPoint;
    lines.push_back(topLine);

    rightLine.startPoint = topRightPoint;
    rightLine.endPoint = bottomRightPoint;
    lines.push_back(rightLine);

    bottomLine.startPoint = bottomRightPoint;
    bottomLine.endPoint = bottomLeftPoint;
    lines.push_back(bottomLine);

    leftLine.startPoint = bottomLeftPoint;
    leftLine.endPoint = topLeftPoint;
    lines.push_back(leftLine);


}

std::vector<line> Obstacle::getLines() {
    return lines;
}