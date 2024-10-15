#ifndef TIMER_H
#define TIMER_H

#include <chrono>

#include "Type.h"

class Timer
{
	public:
		Timer();
		
		double count() const;
		
		void restart();
	
	private:
		using timer = cpp::chrono::steady_clock;
		using instant = cpp::chrono::time_point<timer>;
		
		instant start;
		
		instant now() const;
};

#endif