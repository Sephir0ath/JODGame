#ifndef TYPE_H
#define TYPE_H

#include <array>
#include <functional>
#include <list>
#include <map>
#include <memory>
#include <queue>
#include <set>
#include <stack>
#include <string>
#include <vector>

#define NULLPTR (nullptr)

namespace cpp = std;

using string = cpp::string;

template<typename T> using set = cpp::set<T>;
template<typename T> using list = cpp::list<T>;
template<typename T> using queue = cpp::queue<T>;
template<typename T> using stack = cpp::stack<T>;
template<typename T> using vector = cpp::vector<T>;

template<typename T, int S> using array = cpp::array<T, S>;

template<typename T1, typename T2> using map = cpp::map<T1, T2>;

template<typename T> using pointer = cpp::unique_ptr<T>;
template<typename T> using reference = cpp::reference_wrapper<T>;

#endif