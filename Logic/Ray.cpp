#include "Ray.h"



Ray::Ray(std::vector<float> pos, int angle){
    this->pos = pos;

    dir.push_back(std::cos(angle * M_PI / 180));
    dir.push_back(std::sin(angle * M_PI / 180));
}


std::vector<float> Ray::cast(line wallLine){

    std::vector<float> intersectPoint;
    float x1 = wallLine.startPoint[0];
    float y1 = wallLine.startPoint[1];
    float x2 = wallLine.endPoint[0];
    float y2 = wallLine.endPoint[1];

    float x3 = pos[0];
    float y3 = pos[1];
    float x4 = pos[0] + dir[0];
    float y4 = pos[1] + dir[1];

    float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    
    if(denominator == 0)
    {
        return {};
    }

    float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
    float u = -1 * ((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

    if(t >= 0 && t <= 1 && u >= 0)
    {
        intersectPoint.push_back(x1 + (t * (x2 - x1)));
        intersectPoint.push_back(y1 + (t * (y2 - y1)));
        return intersectPoint;
    } else{
        return {};
    }


}