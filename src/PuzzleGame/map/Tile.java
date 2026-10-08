package PuzzleGame.map;

public class Tile
{
    private boolean walkable;

    public Tile(boolean walkable)
    {
        this.walkable = walkable;
    }

    public boolean isWalkable()
    {
        return walkable;
    }

    public void setWalkable(boolean walkable)
    {
        this.walkable = walkable;
    }
}