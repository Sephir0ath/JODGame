//
// Created by pyrr on 17-09-24.
//

#include "PlayerController.h"

PlayerController::PlayerController(Player *player) {
    this->player = player;

}

void PlayerController::handleInput(float deltaTime) {
    const Uint8* keystate = SDL_GetKeyboardState(NULL);
    

    if (keystate[SDL_SCANCODE_A]) // -> Rota izquierda
    {
        player->direction -= 0.7;
        // player->setPos(0, -player->getVel() * deltaTime);
    }

    if (keystate[SDL_SCANCODE_D]) // -> Rota derecha
    {
        player->direction += 0.7;
        // player->setPos(0, player->getVel() * deltaTime);
    }

    if (keystate[SDL_SCANCODE_W]) // -> Avanza en función de dirección
    {
        //player->setPos(1, -player->getVel() * deltaTime);
        player->pos[0] += player->getVel() * deltaTime * std::cos(player->direction * M_PI / 180.0); // -> Componente x segun dirección radianes
        player->pos[1] += player->getVel() * deltaTime * std::sin(player->direction * M_PI / 180.0); // -> Componente y según dirección radianes
    }

    if (keystate[SDL_SCANCODE_S])
    {
        // player->setPos(1, player->getVel() * deltaTime);
        player->pos[0] -= player->getVel() * deltaTime * std::cos(player->direction * M_PI / 180.0); // -> Componente x segun dirección radianes
        player->pos[1] -= player->getVel() * deltaTime * std::sin(player->direction * M_PI / 180.0); // -> Componente y según dirección radianes
    }

    // Reajuste de los rayos según posición y dirección

    for(int i = 0;i < player->rays.size();i++) {
        
        player->rays[i].pos[0] = player->pos[0];
        player->rays[i].pos[1] = player->pos[1];

        player->rays[i].dir = {std::cos((player->direction + i - 22) * M_PI / 180.0),
                            std::sin((player->direction + i - 22) * M_PI / 180.0)};


    }

}

void PlayerController::render(SDL_Renderer *renderer) {
    std::vector<float> pos = player->getPos();
    float test = 0;
    // SDL_Rect playerRect = {pos[0], pos[1], 10, 10};


    // SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    // SDL_RenderFillRect(renderer, &playerRect);

}


