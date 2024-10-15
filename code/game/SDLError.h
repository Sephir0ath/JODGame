#ifndef SDL_ERROR
#define SDL_ERROR

#include "Type.h"

#include "log/Error.h"

class SDLError : public Error
{
	public:
		SDLError(const string& source, const string& message = "SDL error") : Error(source, message)
		{
		}
};

#endif