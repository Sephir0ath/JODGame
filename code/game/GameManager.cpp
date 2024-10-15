#include "SDL2/SDL.h"

#include "game/GameManager2.h"
#include "game/SDLError.h"
#include "game/Timer.h"

#include "log/Error.h"

GameManager::GameManager() : window(this -> WINDOW_SIZE_X, this -> WINDOW_SIZE_Y)
{
}

void GameManager::run()
{
	const int MAX_UPDATES = 5;
	
	const double FPS = 60;
	const double UPDATE_TIME = 1 / FPS;
	
	if(SDL_Init(SDL_INIT_EVERYTHING) != 0)
		throw SDLError("GameManager::run()");
	
	try
	{
		Timer timer;
		
		double timeRemaining = 0;
		
		while(true)
		{
			timeRemaining += timer.count();
			
			timer.restart();
			
			{
				SDL_Event event;
				
				while(SDL_PollEvent(&event))
				{
					switch(event.type)
					{
						case SDL_KEYDOWN:
						{
							if(event.key.keysym.scancode == SDL_SCANCODE_ESCAPE)
								return;
						} break;
						
						case SDL_QUIT:
							return;
					}
				}
			}
			
			for(int z = 0; (timeRemaining >= UPDATE_TIME) && (z < MAX_UPDATES); z++)
			{
				// actualizar fisicas
				
				timeRemaining -= UPDATE_TIME;
			}
			
			// actualizar graficos
		}
	}
	
	catch(const Error& error)
	{
		error.report();
	}
	
	catch(...)
	{
	}
	
	SDL_Quit();
}