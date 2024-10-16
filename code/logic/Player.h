#ifndef PLAYER_H
#define PLAYER_H

#include <iostream>
#include <vector>

#include "SDL2/SDL.h"

#include "Ray.h"
#include "Obstacle.h"

class Player
{
    public:
		int health;
		const int VEL = 200;
		std::vector<float> pos;
		std::vector<Ray> rays;
		float direction = 0;
		const int MAX_DISTANCE_VIEW = 400;

		Player(std::vector<float> pos);
		void lookWalls(std::vector<Obstacle> &obstacles);
		std::vector<float> getPos();
		int getVel();
		void setPos(int index, int vel);
};

#endif