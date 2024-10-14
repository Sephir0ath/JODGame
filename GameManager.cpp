//
// Created by pyrr on 17-09-24.
//

#include <SDL2/SDL.h>
#include <iostream>
#include <ostream>
#include <vector>

#include "GameManager.h"
#include "Interface/PlayerController.h"
#include "Renderer.h"


GameManager::GameManager() {
    if(SDL_Init(SDL_INIT_VIDEO) != 0)
    {
        std::cout << "SDL_Init Error: " << SDL_GetError() << std::endl;
        exit(1);
    }

    window = SDL_CreateWindow("-", 0, 0, 1280, 720, SDL_WINDOW_SHOWN);
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

}


void GameManager::run(){

    Player player =  Player({400, 500});
    PlayerController playerController = PlayerController(&player);
    Renderer::setRenderer(renderer);

    while(true) {
        
        currentTime = SDL_GetTicks();
        deltaTime = (currentTime - lastTime) / 1000.0f;
        lastTime = currentTime;

        SDL_Event event;
        SDL_PollEvent(&event);

        if(event.type == SDL_QUIT)
        {
            exit(0);
        }

        if(event.type == SDL_KEYDOWN)
        {
            if(event.key.keysym.scancode == SDL_SCANCODE_ESCAPE)
            {
                exit(0);
            }
        }

        SDL_SetRenderDrawColor(renderer, 120, 120, 120, 255);
        SDL_RenderClear(renderer);

        playerController.handleInput(deltaTime);
        player.lookWalls();
        playerController.render(renderer);

        SDL_RenderPresent(renderer);

    }
}
