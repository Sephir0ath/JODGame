#include <iostream>

#include "log/Error.h"

Error::Error(const string& source, const string& message = "") : source(source), message(message)
{
}

void Error::report() const
{
	cpp::cerr << "[ERROR] " << this -> source << ": " << this -> message << '\n';
}

const string& Error::getSource() { return this -> source; }
const string& Error::getMessage() { return this -> message; }