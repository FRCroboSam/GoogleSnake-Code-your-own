# GoogleSnake Create Your Own Project AP CS A
Team: Samuel Wang
Pre-req: Java 17

How to
- press arrow keys(not left) to start playing
- Game over when the snake head hits itself (blue snake body) or the snake travels out of bounds (the checkerboard)

Purpose
- Play Snake with arrow keys, eat the apples (red boxes) to increase score

Reqs
- User input: arrow keys, entering name to save score
- Hashmap - used for scoreboard (name, score)
- Serialization - scores are saved locally after user dies in game 
- Exception Handling - used in serialization in case there is no leaderboard present
