package PuzzleGame.core;

import PuzzleGame.ui.GamePanel;
import PuzzleGame.player.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
                return;
            }

            if (gp.testPlayer == null || gp.map == null) {
                return;
            }

            switch (code) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    gp.testPlayer.move(Direction.UP, gp.map);
                    break;

                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    gp.testPlayer.move(Direction.DOWN, gp.map);
                    break;

                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    gp.testPlayer.move(Direction.LEFT, gp.map);
                    break;

                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    gp.testPlayer.move(Direction.RIGHT, gp.map);
                    break;
            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.titleState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
