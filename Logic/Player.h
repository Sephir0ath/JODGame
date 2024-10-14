//
// Created by pyrr on 17-09-24.
//

#ifndef PLAYER_H
#define PLAYER_H

#include <iostream>
#include <SDL2/SDL.h>
#include <vector>

#include "Ray.h"

class Player {
    public:
        int health;
        const int VEL = 150;
        std::vector<float> pos;
        std::vector<Ray> rays;
        float direction = 0;
        const int MAX_DISTANCE_VIEW = 400;

    
        Player(std::vector<float> pos);
        void lookWalls();
        std::vector<float> getPos();
        int getVel();
        void setPos(int index, int vel);

};




#endif //PLAYER_H
