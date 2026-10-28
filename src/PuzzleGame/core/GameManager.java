package PuzzleGame.core;

import PuzzleGame.level.Level;
import PuzzleGame.level.LevelManager;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;

    private final LevelManager levelManager = new LevelManager();
    private final List<GameListener> listeners = new ArrayList<>();
    private GameState state = GameState.MENU;
    private int score = 0;
    private int levelStartScore = 0; // Lưu mốc điểm tích lũy an toàn khi bước vào màn chơi hiện tại

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void addListener(GameListener l) {
        if (l != null && !listeners.contains(l)) listeners.add(l);
    }

    public void removeListener(GameListener l) { listeners.remove(l); }

    public GameState getState() { return state; }
    public boolean isPlaying() { return state == GameState.PLAYING; }
    public Level getCurrentLevel() { return levelManager.getCurrentLevel(); }
    public LevelManager getLevelManager() { return levelManager; }

    public int getScore() { return score; }
    public void addScore(int points) { this.score += points; }
    public void resetScore() { 
        this.score = 0; 
        this.levelStartScore = 0;
    }

    // Start Game: về Level 1 và bắt đầu chơi
    public void startGame() {
        resetScore();
        levelManager.loadFirstLevel();
        setState(GameState.PLAYING);
        notifyLevelLoaded();
    }

    public void pauseGame() {
        if (state == GameState.PLAYING) setState(GameState.PAUSED);
    }

    public void resumeGame() {
        if (state == GameState.PAUSED) setState(GameState.PLAYING);
    }

    public void togglePause() {
        if (state == GameState.PLAYING) pauseGame();
        else if (state == GameState.PAUSED) resumeGame();
    }

    /**
     * Khởi động lại màn chơi hiện tại (phím R):
     * Hoàn tác điểm số về mốc ban đầu của màn chơi này (levelStartScore),
     * bảo lưu toàn bộ điểm số người chơi đã tích lũy từ các màn trước đó.
     */
    public void restartLevel() {
        if (state != GameState.PLAYING && state != GameState.PAUSED) return;
        score = levelStartScore;
        levelManager.restartLevel();
        setState(GameState.PLAYING);
        notifyLevelLoaded();
    }

    // Gọi khi Player mở Door và đi vào
    public void completeLevel() {
        if (state != GameState.PLAYING) return;
        if (levelManager.isLastLevel()) winGame();
        else nextLevel();
    }

    public void nextLevel() {
        levelStartScore = score; // Lưu mốc điểm tích lũy của các màn đã hoàn thành
        Level next = levelManager.nextLevel();
        if (next == null) {
            winGame();
            return;
        }
        notifyLevelLoaded();
    }

    public void winGame() {
        setState(GameState.WIN);
        for (GameListener l : new ArrayList<>(listeners)) l.onGameWon();
    }

    public void backToMenu() { 
        resetScore();
        setState(GameState.MENU); 
    }

    private void setState(GameState newState) {
        if (state == newState) return;
        state = newState;
        for (GameListener l : new ArrayList<>(listeners)) l.onStateChanged(newState);
    }

    private void notifyLevelLoaded() {
        Level level = levelManager.getCurrentLevel();
        for (GameListener l : new ArrayList<>(listeners)) l.onLevelLoaded(level);
    }
}
