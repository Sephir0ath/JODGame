//
// Created by pyrr on 17-09-24.
//

#include <vector>

#include "Player.h"
#include "Ray.h"
#include "Renderer.h"


Player::Player(std::vector<float> position) {
    this->pos = position;

    for(int i = -22;i < 22;i++){
        
        Ray ray = Ray(pos, direction + i);
        rays.push_back(ray);
    
    }

}

void Player::lookWalls(std::vector<Obstacle> &obstacles) {
    
    
    for(Ray &ray : rays) {
        
        float minDistance = 10000;
        std::vector<float> closestIntersectPoint;
        Obstacle *closestObstacle;

        for(Obstacle &obstacle : obstacles) {

            const std::vector<line> &lines = obstacle.getLines();
            for(const line &line : lines) {
                
                std::vector<float> intersectPoint = ray.cast(line);
                if(!intersectPoint.empty()) {

                    float x1 = ray.pos[0];
                    float y1 = ray.pos[1];
                    float x2 = intersectPoint[0];
                    float y2 = intersectPoint[1];
                    float dist = std::sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)); // -> Distancia Euclidiana al punto interseccion

                    if(dist < minDistance) // -> Se comprueba si hay nuevo obstáculo más cercano
                    {

                        minDistance = dist;
                        closestIntersectPoint = intersectPoint;
                        closestObstacle = &obstacle;
                        
                    }

                }


            }

        }
        
        std::vector<float> endPoint;
        if(!closestIntersectPoint.empty() && minDistance <= MAX_DISTANCE_VIEW) {

            endPoint.push_back(closestIntersectPoint[0]);
            endPoint.push_back(closestIntersectPoint[1]);

        } else {

            endPoint.push_back(ray.pos[0] + ray.dir[0] * MAX_DISTANCE_VIEW);
            endPoint.push_back(ray.pos[1] + ray.dir[1] * MAX_DISTANCE_VIEW);
        }

        
        Renderer::renderRay(pos, endPoint);


    }
    



}


std::vector<float> Player::getPos() {
    return this->pos;
}

void Player::setPos(int index, int vel) {
    this->pos[index] += vel;
}

int Player::getVel() {
    return VEL;
}


