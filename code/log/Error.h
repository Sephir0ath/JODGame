#ifndef ERROR_H
#define ERROR_H

#include "Type.h"

class Error
{
	public:
		Error(const string& source, const string& message);
		
		void report() const;
		
		const string& getSource();
		const string& getMessage();
	
	private:
		const string source;
		const string message;
};

#endif