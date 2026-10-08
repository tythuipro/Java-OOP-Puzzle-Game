package PuzzleGame.objects;

import PuzzleGame.map.Map;
import PuzzleGame.player.Direction;
import PuzzleGame.player.Player;

public class Box extends GameObject
{
    public Box(int x, int y)
    {
        super(x, y);
    }

    public boolean push(Player player, Direction direction, Map map)
    {
        int newX = x;
        int newY = y;

        switch (direction)
        {
            case UP:
                newY--;
                break;

            case DOWN:
                newY++;
                break;

            case LEFT:
                newX--;
                break;

            case RIGHT:
                newX++;
                break;
        }

        int playerX = x;
        int playerY = y;

        switch (direction)
        {
            case UP:
                playerY++;
                break;

            case DOWN:
                playerY--;
                break;

            case LEFT:
                playerX++;
                break;

            case RIGHT:
                playerX--;
                break;
        }

        if (player.getX() != playerX || player.getY() != playerY)
        {
            return false;
        }

        if (!map.isWalkable(newX, newY))
        {
            return false;
        }

        int oldX = x;
        int oldY = y;

        x = newX;
        y = newY;

        player.setPosition(oldX, oldY);
        player.setDirection(direction);

        return true;
    }
}