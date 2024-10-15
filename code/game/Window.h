#ifndef WINDOW_H
#define WINDOW_H

#include "Type.h"

class SDL_Window;
class SDL_Renderer;

class Window
{
	public:
		Window(const int& sizeX, const int& sizeY);
		~Window();
	
	private:
		SDL_Window* window;
		SDL_Renderer* renderer;
};

#endif