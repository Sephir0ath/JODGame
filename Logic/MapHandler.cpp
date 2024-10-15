#include <fstream>
#include <iostream>

#include "MapHandler.h"

std::vector<std::vector<int>> MapHandler::readMap(std::string fileName) { // -> Para matriz nxn, n par

    std::ifstream mapFile(fileName);
    if(!mapFile.is_open()) {
        return {};
    }

    std::string line;
    std::getline(mapFile, line); // -> Lee primera línea para determinar orden
    int n = line.size(); // -> Orden matriz mapa

    
    std::vector<std::vector<int>> map(n, std::vector<int>(n));
    for (int j = 0; j < n; j++) {
        map[0][j] = line[j] - '0';
    }

    for(int i = 1;i < n;i++) {
        
        std::getline(mapFile, line);
        
        for(int j = 0;j < n;j++) {
            map[i][j] = line[j] - '0';
        }

    }

    return map;

}

void MapHandler::fillObstacles(std::vector<std::vector<int>> &map, std::vector<Obstacle> &obstacles) {
    
    float obstacleSize = 800 / map.size();
    std::vector<float> topLeftPoint;
    
    for(int i = 0;i < map.size();i++) {
        for(int j = 0;j < map.size();j++) {
            
            if(map[i][j] == 1) { // -> Para obstáculo

                topLeftPoint = {j * obstacleSize, i * obstacleSize};
                Obstacle obstacle = Obstacle(topLeftPoint, obstacleSize);
                obstacles.push_back(obstacle);
                
            }
            
        }
    }


}