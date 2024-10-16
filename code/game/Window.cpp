#include "SDL2/SDL.h"

#include "game/SDLError.h"
#include "game/Window.h"

Window::Window(const int& sizeX, const int& sizeY)
{
	if(SDL_InitSubSystem(SDL_INIT_VIDEO) != 0)
		throw SDLError("Window::Window()");
	
	this -> window = SDL_CreateWindow("", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, sizeX, sizeY, (SDL_WINDOW_SHOWN | SDL_WINDOW_BORDERLESS));
	
	if(this -> window == NULLPTR)
		throw SDLError("Window::Window()");
	
	this -> renderer = SDL_CreateRenderer(this -> window, -1, 0);
	
	if(this -> renderer == NULLPTR)
		throw SDLError("Window::Window()");
}

Window::~Window()
{
	SDL_DestroyRenderer(this -> renderer);
	SDL_DestroyWindow(this -> window);
}

SDL_Renderer* Window::getRenderer()
{
	return this -> renderer;
}