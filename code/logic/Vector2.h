#ifndef VECTOR2_H
#define VECTOR2_H

class Vector2
{
    public:
        Vector2(const int x, const int y);
        
        static double dotProduct(const Vector2& vectorA, const Vector2& vectorB);
        
        Vector2 operator+(const Vector2& vectorB);
        
        bool operator==(const Vector2& vectorB);
    
    private:
        int x;
        int y;
};

#endif