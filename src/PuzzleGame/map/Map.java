package PuzzleGame.map;

import java.util.ArrayList;
import java.util.List;

import PuzzleGame.objects.Box;

public class Map
{
    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Box> boxes;

    public Map(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];
        this.boxes = new ArrayList<>();
    }

    public boolean isWalkable(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
        {
            return false;
        }

        return tiles[y][x] != null && tiles[y][x].isWalkable();
    }

    public boolean hasBox(int x, int y)
    {
        for (Box box : boxes)
        {
            if (box.getX() == x && box.getY() == y)
            {
                return true;
            }
        }

        return false;
    }

    public void addBox(Box box)
    {
        boxes.add(box);
    }

    public Tile getTile(int x, int y)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
        {
            return null;
        }

        return tiles[y][x];
    }

    public void setTile(int x, int y, Tile tile)
    {
        if (x < 0 || x >= width || y < 0 || y >= height)
        {
            return;
        }

        tiles[y][x] = tile;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}