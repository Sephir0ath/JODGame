#include "Player.h"

#include <vector>

Player::Player(std::vector<int> position) {
	this->pos = position;
}

std::vector<int> Player::getPos() {
	return this->pos;
}

void Player::setPos(int index, int vel) {
	this->pos[index] += vel;
}

int Player::getVel() {
	return VEL;
}