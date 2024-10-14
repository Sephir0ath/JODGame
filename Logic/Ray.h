#ifndef RAY_H
#define RAY_H

#include <iostream>
#include <vector>
#include <SDL2/SDL.h>
#include <cmath>

struct line{
    std::vector<float> startPoint;
    std::vector<float> endPoint;

};

class Ray{

    public:
        std::vector<float> pos;
        std::vector<double> dir;
        Ray(std::vector<float> pos, int angle);
        std::vector<float> cast(line wallLine);

        

};

#endif