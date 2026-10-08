package PuzzleGame.player;

import PuzzleGame.map.Map;
import PuzzleGame.objects.GameObject;

public class Player extends GameObject
{
    private Direction direction;
    private boolean hasKey;

    public Player(int x, int y)
    {
        super(x, y);
        direction = Direction.DOWN;
        hasKey = false;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public boolean hasKey()
    {
        return hasKey;
    }

    public void setHasKey(boolean hasKey)
    {
        this.hasKey = hasKey;
    }

    public boolean move(Direction direction, Map map)
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

        if (!map.isWalkable(newX, newY))
        {
            return false;
        }

        x = newX;
        y = newY;
        this.direction = direction;

        return true;
    }
}