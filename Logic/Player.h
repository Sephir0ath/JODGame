//
// Created by pyrr on 17-09-24.
//

#ifndef PLAYER_H
#define PLAYER_H

#include <iostream>
#include <SDL2/SDL.h>
#include <vector>

class Player {
    private:
        int health;
        const int VEL = 70;
        std::vector<int> pos;
    public:
        Player(std::vector<int> pos);
        std::vector<int> getPos();
        int getVel();
        void setPos(int index, int vel);

};




#endif //PLAYER_H
