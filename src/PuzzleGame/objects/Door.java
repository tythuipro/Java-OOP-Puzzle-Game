package PuzzleGame.objects;

import PuzzleGame.player.Player;

public class Door extends GameObject
{
    private boolean locked;

    public Door(int x, int y)
    {
        super(x, y);
        locked = true;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean unlock(Player player)
    {
        if (!locked)
        {
            return true;
        }

        if (!player.hasKey())
        {
            return false;
        }

        locked = false;
        return true;
    }
}