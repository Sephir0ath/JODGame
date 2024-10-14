//
// Created by pyrr on 17-09-24.
//

#include "GameManager.h"
#include <SDL2/SDL.h>
#include <iostream>
#include <ostream>
#include <vector>
#include "Interface/PlayerController.h"

GameManager::GameManager() {
    if(SDL_Init(SDL_INIT_VIDEO) != 0)
    {
        std::cout << "SDL_Init Error: " << SDL_GetError() << std::endl;
        exit(1);
    }

    window = SDL_CreateWindow("-", 0, 0, 1280, 720, SDL_WINDOW_SHOWN);
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

}

void GameManager::runLevel() {
    // LEER ARCHIVO DE NIVEL - OBTENER MATRIZ - RENDERIZAR NIVEL A PARTIR DE MATRIZ
    //Player player(pos);
    //playerController(player);
    //obj
    std::vector<int> pos = {400, 500};
    Player player =  Player(pos);
    PlayerController playerController = PlayerController(&player);

    SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
    SDL_RenderClear(renderer);


    playerController.handleInput(0.016);

    playerController.render(renderer);

    SDL_RenderPresent(renderer);











    /*
        while (true) {
        //player.updatepos();
        // Handle collision
        //render("player.png", player.getX, player.getY);
        renderCopy
        switch gameObject:
            case 1:
                enemigo.render();
                rendercpy("enemi
            case 2:
                wall.render();

            case 3:
                playerController.render();


        for()
            render(gameobject[i],


    */
}


void GameManager::run(){

    std::vector<int> pos = {400, 500};
    Player player =  Player(pos);
    PlayerController playerController = PlayerController(&player);

    while(true) {

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

        SDL_SetRenderDrawColor(renderer, 154, 15, 162, 255);
        SDL_RenderClear(renderer);

        playerController.handleInput(0.016);
        playerController.render(renderer);

        SDL_RenderPresent(renderer);

    }
}
