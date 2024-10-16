#include "SDL2/SDL.h"

#include "game/GameManager.h"
#include "game/SDLError.h"
#include "game/Timer.h"

#include "log/Error.h"

#include "GameManager.h"
#include "PlayerController.h"
#include "Renderer.h"
#include "MapHandler.h"

#include "Obstacle.h"

#include <stdio.h>

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
	
	Player player = Player({400, 500});
	PlayerController playerController = PlayerController(&player);
	
	Renderer::setRenderer(this -> window.getRenderer());
	
	vector<vector<int>> map = MapHandler::readMap("./content/map1"); // -> Lee y carga matriz del mapa;
	vector<Obstacle> obstacles;
	
	MapHandler::fillObstacles(map, obstacles);
	
	try
	{
		Timer timer;
		
		// double timeRemaining = 0;
		
		while(true)
		{
			// timeRemaining += timer.count();
			
			double time = timer.count();
			
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
			
			/*
			for(int z = 0; (timeRemaining >= UPDATE_TIME) && (z < MAX_UPDATES); z++)
			{
				// actualizar fisicas
				
				timeRemaining -= UPDATE_TIME;
			}
			*/
			
			// actualizar graficos
			
			SDL_SetRenderDrawColor(this -> window.getRenderer(), 0, 0, 0, 100);
			SDL_RenderClear(this -> window.getRenderer());
			
			Renderer::renderMap(obstacles);
			
			playerController.handleInput(time);
			
			player.lookWalls(obstacles);
			playerController.render(this -> window.getRenderer());
			
			SDL_RenderPresent(this -> window.getRenderer());
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