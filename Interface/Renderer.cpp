#include "Renderer.h"

#include <SDL2/SDL.h>

SDL_Renderer* Renderer::renderer = nullptr;

void Renderer::setRenderer(SDL_Renderer *rend){
    Renderer::renderer = rend;
}

void Renderer::renderRay(std::vector<float> pos, std::vector<float> endPoint){

    SDL_SetRenderDrawColor(Renderer::renderer, 255, 0, 0, 255);
    SDL_RenderDrawLine(Renderer::renderer, pos[0], pos[1], endPoint[0], endPoint[1]);

}

void Renderer::renderObstacle(Obstacle *obstacle) {
    
    SDL_Rect obstacleRect;

    obstacleRect.x = obstacle->topLeftPoint[0];
    obstacleRect.y = obstacle->topLeftPoint[1];
    obstacleRect.w = obstacle->obstacleSize;
    obstacleRect.h = obstacle->obstacleSize;

    SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    SDL_RenderFillRect(renderer, &obstacleRect);



}

void Renderer::renderMap(std::vector<Obstacle> &obstacles) {

    for(Obstacle &obstacle : obstacles) {
        Renderer::renderObstacle(&obstacle);
    }
   
}