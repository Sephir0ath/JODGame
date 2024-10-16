#ifndef RENDERER_H
#define RENDERER_H

#include <SDL2/SDL.h>
#include <vector>

#include "Ray.h"
#include "Obstacle.h"

class Renderer{
    private:
        static SDL_Renderer* renderer;
    public:
        static void setRenderer(SDL_Renderer* rend);
        static void renderRay(std::vector<float> pos, std::vector<float> endPoint);
        static void renderObstacle(Obstacle *obstacle);
        static void renderMap(std::vector<Obstacle> &obstacles); // -> Eventualmente debe renderizar tambien enemigos
};









#endif
