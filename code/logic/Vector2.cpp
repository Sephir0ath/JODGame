#include "Vector2.h"

Vector2::Vector2(const int x, const int y) : x(x), y(y)
{
}

Vector2::dotProduct(const Vector2& vectorA, const Vector2& vectorB)
{
    return vectorA.x * vectorB.x + vectorA.y * vectorB.y;
}

Vector2 Vector2::operator+(const Vector2& vectorB)
{
    return Vector2(); // Por implementar
}

bool Vector2::operator==(const Vector2& vectorB)
{
    return (this -> x == vectorB.x) && (this -> y == vectorB.y);
}