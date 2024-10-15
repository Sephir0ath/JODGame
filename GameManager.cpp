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
#include "MapHandler.h"


#include "Obstacle.h"

GameManager::GameManager() {
    if(SDL_Init(SDL_INIT_VIDEO) != 0)
    {
        std::cout << "SDL_Init Error: " << SDL_GetError() << std::endl;
        exit(1);
    }

    window = SDL_CreateWindow("-", 0, 0, WIDTH, HEIGHT, SDL_WINDOW_SHOWN);
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

}


void GameManager::run(){

    Player player =  Player({400, 500});
    PlayerController playerController = PlayerController(&player);
    Renderer::setRenderer(renderer);
    
    std::vector<std::vector<int>> map = MapHandler::readMap("mapTest"); // -> Lee y carga matriz del mapa;
    std::vector<Obstacle> obstacles;

    MapHandler::fillObstacles(map, obstacles);
    



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



        SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
        SDL_RenderClear(renderer);

        // Test array obstacles para raycasting (luego cargo obstaculos desde archivo)

        // std::vector<Obstacle> obstacles;
        
        // Obstacle obstacle = Obstacle({0,0}, 80);
        // Renderer::renderObstacle(&obstacle);
        // obstacles.push_back(obstacle);
        // Obstacle obstacle2 = Obstacle({100,100}, 80);
        // Renderer::renderObstacle(&obstacle2);
        // obstacles.push_back(obstacle2);

        Renderer::renderMap(obstacles); // -> Renderización del mapa;

        


        playerController.handleInput(deltaTime);
        player.lookWalls(obstacles);
        playerController.render(renderer);

        SDL_RenderPresent(renderer);

    }
}
