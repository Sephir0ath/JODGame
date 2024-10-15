#include "PlayerController.h"

PlayerController::PlayerController(Player *player) {
    this->player = player;

}

void PlayerController::handleInput(float timeStep) {
    const Uint8* keystate = SDL_GetKeyboardState(NULL);
    std::vector<int> pos = player->getPos();

    if (keystate[SDL_SCANCODE_A])
    {
        player->setPos(0, -player->getVel() * timeStep);
    }

    if (keystate[SDL_SCANCODE_D])
    {
        player->setPos(0, player->getVel() * timeStep);
    }

    if (keystate[SDL_SCANCODE_W])
    {
        player->setPos(1, -player->getVel() * timeStep);
    }

    if (keystate[SDL_SCANCODE_S])
    {
        player->setPos(1, player->getVel() * timeStep);
    }

}

void PlayerController::render(SDL_Renderer *renderer) {
    std::vector<int> pos = player->getPos();
    float test = 0;
    SDL_Rect playerRect = {pos[0], pos[1], 10, 10};


    SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    SDL_RenderFillRect(renderer, &playerRect);

}


