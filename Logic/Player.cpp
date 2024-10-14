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

void Player::lookWalls() {
    
    // Por ahora renderizar solo hacia adelante en 45 grados
    for(Ray &ray : rays) {

        std::vector<float> endPoint;

        endPoint.push_back(ray.pos[0] + ray.dir[0] * MAX_DISTANCE_VIEW);
        endPoint.push_back(ray.pos[1] + ray.dir[1] * MAX_DISTANCE_VIEW);


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


