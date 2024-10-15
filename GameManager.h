//
// Created by pyrr on 17-09-24.
//

#ifndef GAMEMANAGER_H
#define GAMEMANAGER_H
#include <SDL2/SDL.h>

class GameManager {
    private:
        SDL_Window* window;
        SDL_Renderer* renderer;
        const int WIDTH = 800;
        const int HEIGHT = 800;
        float currentTime;
        float deltaTime;
        float lastTime = 0;
    public:
        GameManager();
        void run();

};



#endif //GAMEMANAGER_H
