#ifndef MAPHANDLER_H
#define MAPHANDLER_H

#include <vector>

#include "Obstacle.h"


class MapHandler{

    public:
        static std::vector<std::vector<int>> readMap(std::string fileName); // -> Crea matriz del mapa y se la manda a Renderer. Funciona para matriz nxn, n par
        static void fillObstacles(std::vector<std::vector<int>> &map, std::vector<Obstacle> &obstacles);

};













#endif