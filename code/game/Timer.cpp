#include "game/Timer.h"

Timer::Timer() : start(this -> now())
{
}

double Timer::count() const
{
	using namespace cpp::chrono;
	
	Timer::instant finish = this -> now();
	
	return (duration_cast<milliseconds>(finish - (this -> start)).count()) / 1000.0;
}

void Timer::restart()
{
	this -> start = this -> now();
}

Timer::instant Timer::now() const
{
	return Timer::timer::now();
}