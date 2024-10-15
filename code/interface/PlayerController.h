//
// Created by pyrr on 17-09-24.
//

#ifndef PLAYERCONTROLLER_H
#define PLAYERCONTROLLER_H
#include "../Logic/Player.h"


class PlayerController {
    Player *player;
    public:
        PlayerController(Player *player);
        void handleInput(float timeStep);
        void render(SDL_Renderer* renderer);

};



#endif //PLAYERCONTROLLER_H