package PuzzleGame.level;

import java.util.ArrayList;
import java.util.List;

public class LevelManager
{
    private List<Level> levels;
    private int currentLevel;

    public LevelManager()
    {
        levels = new ArrayList<>();
        currentLevel = 0;
    }

    public void addLevel(Level level)
    {
        levels.add(level);
    }

    public Level getCurrentLevel()
    {
        if (levels.isEmpty())
        {
            return null;
        }

        return levels.get(currentLevel);
    }

    public boolean nextLevel()
    {
        if (currentLevel + 1 >= levels.size())
        {
            return false;
        }

        currentLevel++;
        return true;
    }

    public int getCurrentLevelNumber()
    {
        return currentLevel + 1;
    }

    public int getLevelCount()
    {
        return levels.size();
    }
}