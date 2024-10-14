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