package PuzzleGame.core;

import PuzzleGame.level.LevelManager;

public class GameManager
{
    private GameState gameState;
    private LevelManager levelManager;

    public GameManager(LevelManager levelManager)
    {
        this.levelManager = levelManager;
        gameState = GameState.MENU;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void startGame()
    {
        gameState = GameState.PLAYING;
    }

    public void pauseGame()
    {
        if (gameState == GameState.PLAYING)
        {
            gameState = GameState.PAUSED;
        }
    }

    public void resumeGame()
    {
        if (gameState == GameState.PAUSED)
        {
            gameState = GameState.PLAYING;
        }
    }

    public void winGame()
    {
        gameState = GameState.WIN;
    }

    public boolean nextLevel()
    {
        if (levelManager.nextLevel())
        {
            gameState = GameState.PLAYING;
            return true;
        }

        gameState = GameState.WIN;
        return false;
    }

    public LevelManager getLevelManager()
    {
        return levelManager;
    }
}