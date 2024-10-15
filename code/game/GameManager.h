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
        const int WIDTH = 1280;
        const int HEIGHT = 720;
    public:
        GameManager();
        void run();
        void runLevel();

};



#endif //GAMEMANAGER_H
