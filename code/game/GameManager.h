#ifndef GAME_MANAGER_H
#define GAME_MANAGER_H

#include "Type.h"

#include "game/Window.h"

class GameManager
{
	public:
		GameManager();
		
		void run();
	
	private:
		const int WINDOW_SIZE_X = 1280;
		const int WINDOW_SIZE_Y = 720;
		
		Window window;
};

#endif