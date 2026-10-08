package PuzzleGame.objects;

public class Switch extends GameObject
{
    private boolean active;

    public Switch(int x, int y)
    {
        super(x, y);
        active = false;
    }

    public boolean isActive()
    {
        return active;
    }

    public void activate()
    {
        active = true;
    }

    public void deactivate()
    {
        active = false;
    }

    public void check(Box box)
    {
        if (box.getX() == x && box.getY() == y)
        {
            activate();
        }
    }
}