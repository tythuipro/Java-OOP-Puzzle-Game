package PuzzleGame.teleport;

import PuzzleGame.objects.GameObject;
import PuzzleGame.player.Player;

public class Teleporter extends GameObject
{
    private int targetX;
    private int targetY;

    public Teleporter(int x, int y, int targetX, int targetY)
    {
        super(x, y);
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public int getTargetX()
    {
        return targetX;
    }

    public int getTargetY()
    {
        return targetY;
    }

    public boolean teleport(Player player)
    {
        if (player.getX() != x || player.getY() != y)
        {
            return false;
        }

        player.setPosition(targetX, targetY);
        return true;
    }
}
