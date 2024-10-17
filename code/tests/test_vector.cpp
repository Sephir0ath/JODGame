#include "Vector2.h"

#include <cassert>

void testVector()
{
    Vector2 a(2, 4);
    Vector2 b(3, 5);
    
    {
        Vector2 res = a + b; // resultado del metodo
        Vector2 out = Vector2(5, 9); // resultado esperado
        
        assert(res == out);
    }
    
    {
        double res = Vector2::dotProduct(a, b); // resultado del metodo
        double out = 26; // resultado esperado
        
        assert(res == out);
    }
}