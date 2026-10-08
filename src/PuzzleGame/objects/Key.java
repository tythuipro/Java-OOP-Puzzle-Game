package PuzzleGame.objects;

import PuzzleGame.player.Player;

public class Key extends GameObject
{
    private boolean collected;

    public Key(int x, int y)
    {
        super(x, y);
        collected = false;
    }

    public boolean isCollected()
    {
        return collected;
    }

    public void collect(Player player)
    {
        if (!collected && player.getX() == x && player.getY() == y)
        {
            collected = true;
            player.setHasKey(true);
        }
    }
}