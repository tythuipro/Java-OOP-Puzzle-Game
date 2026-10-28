package PuzzleGame.ui;

import PuzzleGame.map.Map;
import PuzzleGame.player.Player;
import PuzzleGame.objects.Box;
import PuzzleGame.objects.Door;
import PuzzleGame.objects.Switch;
import PuzzleGame.objects.Coin;
import PuzzleGame.teleport.Teleporter;
import PuzzleGame.teleport.TeleportManager;
import PuzzleGame.core.KeyHandler;
import PuzzleGame.core.GameManager;
import PuzzleGame.core.GameState;
import PuzzleGame.core.GameListener;
import PuzzleGame.level.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, GameListener {

    // CÀI ĐẶT MÀN HÌNH (12 Cột x 9 Hàng, 64x64 pixel mỗi ô)
    final int originalTileSize = 16; 
    final int scale = 4;
    public final int tileSize = originalTileSize * scale; // 64x64
    
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;  // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    public Map map;
    public UI ui = new UI(this); 
    
    Thread gameThread;
    int fps = 60;

    public BufferedImage imagePlayer, imageBox, imageSwitch;
    public BufferedImage imageCoin, imageDoor, imageTeleport, imageTeleport2;
    public BufferedImage imagePlayerUp, imagePlayerDown, imagePlayerLeft, imagePlayerRight;
    
    // TẠO OBJECT ĐỂ TEST VẼ
    public Player testPlayer;
    public Box testBox;
    public Switch testSwitch;
    public Coin testCoin;
    public Door testDoor;
    public TeleportManager teleManager = new TeleportManager();

    // TRẠNG THÁI GAME
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int winState = 3;

    // BỘ XỬ LÝ BÀN PHÍM
    KeyHandler keyH = new KeyHandler(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); 
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        gameState = titleState; // Bắt đầu mở lên là màn hình Menu
        
        map = new Map(maxScreenRow, maxScreenCol, tileSize);
        GameManager.getInstance().addListener(this);

        loadObjectImages();
    }

    private void loadObjectImages() {
        try {
            imagePlayerDown = ImageIO.read(new File("assets/images/player/player_down_idle.png"));
            imagePlayerUp = ImageIO.read(new File("assets/images/player/player_up_idle.png"));
            imagePlayerLeft = ImageIO.read(new File("assets/images/player/player_left_idle.png"));
            imagePlayerRight = ImageIO.read(new File("assets/images/player/player_right_idle.png"));
            imagePlayer = imagePlayerDown;

            imageBox = ImageIO.read(new File("assets/images/puzzle objects/box.png"));
            imageSwitch = ImageIO.read(new File("assets/images/puzzle objects/switch.png"));
            
            File coinFile = new File("assets/images/puzzle objects/coin.png");
            if (coinFile.exists()) {
                imageCoin = ImageIO.read(coinFile);
            }

            imageDoor = ImageIO.read(new File("assets/images/puzzle objects/door closed.png"));
            imageTeleport = ImageIO.read(new File("assets/images/teleporter/teleporter_1.png"));
            imageTeleport2 = ImageIO.read(new File("assets/images/teleporter/teleporter_2.png"));
        } catch (IOException e) {
            System.out.println("Lỗi load ảnh nhân vật/đồ vật!");
            e.printStackTrace();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint(); 
                delta--;
            }
        }
    }

    public void update() {
        // Cập nhật UI animations (nhấp nháy text, ngôi sao...)
        ui.update();
    }

    /**
     * Khởi động lại màn chơi hiện tại (reset vị trí người chơi, hộp, công tắc, coin, cửa và teleport).
     */
    public void resetGame() {
        GameManager manager = GameManager.getInstance();
        if (gameState == titleState || gameState == winState) {
            manager.startGame();
        } else {
            manager.restartLevel();
        }
    }

    public int getCurrentLevelNumber() {
        Level level = GameManager.getInstance().getCurrentLevel();
        return level == null ? 0 : level.getNumber();
    }

    public int getScore() {
        return GameManager.getInstance().getScore();
    }

    @Override
    public void onStateChanged(GameState state) {
        switch (state) {
            case MENU: gameState = titleState; break;
            case PLAYING: gameState = playState; break;
            case PAUSED: gameState = pauseState; break;
            case WIN: gameState = winState; break;
        }
    }

    @Override
    public void onLevelLoaded(Level level) {
        if (level == null) return;

        int[][] levelData = new int[maxScreenRow][maxScreenCol];
        for (int row = 0; row < maxScreenRow; row++) {
            java.util.Arrays.fill(levelData[row], 1);
        }
        for (int row = 0; row < Math.min(level.getRows(), maxScreenRow); row++) {
            for (int col = 0; col < Math.min(level.getCols(), maxScreenCol); col++) {
                levelData[row][col] = level.getTile(row, col) == Level.WALL ? 1 : 0;
            }
        }
        map.loadMapData(levelData);

        Point start = level.getPlayerStart();
        testPlayer = start == null ? null : new Player(start.x, start.y);
        Point box = firstPosition(level.getBoxes());
        testBox = box == null ? null : new Box(box.x, box.y);
        
        Point coinPos = firstPosition(level.getCoins());
        testCoin = coinPos == null ? null : new Coin(coinPos.x, coinPos.y);
        
        Point door = firstPosition(level.getDoors());
        testDoor = door == null ? null : new Door(door.x, door.y);
        Point floorSwitch = firstPosition(level.getSwitches());
        testSwitch = floorSwitch == null ? null : new Switch(floorSwitch.x, floorSwitch.y);

        teleManager.clear();
        List<Point> teleporters = level.getTeleporters();
        if (teleporters.size() >= 2) {
            Point first = teleporters.get(0);
            Point second = teleporters.get(1);
            teleManager.addTeleporterPair(first.x, first.y, second.x, second.y);
        }
    }

    private Point firstPosition(List<Point> positions) {
        return positions.isEmpty() ? null : positions.get(0);
    }

    @Override
    public void onGameWon() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Bật anti-aliasing cho chất lượng render tốt hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (gameState == titleState) {
            ui.draw(g2); // Chỉ vẽ Menu
        } else {
            // 1. Vẽ Map
            map.draw(g2);

            // 2. Vẽ Đồ vật (Switch vẽ dưới cùng để Box/Player đè lên)
            BufferedImage swImg = (testSwitch != null && testSwitch.getImage() != null) ? testSwitch.getImage() : imageSwitch;
            if (swImg != null && testSwitch != null) {
                g2.drawImage(swImg, testSwitch.getX() * tileSize, testSwitch.getY() * tileSize, tileSize, tileSize, null);
            }

            // Vẽ Coin (nếu chưa được nhặt)
            if (testCoin != null && !testCoin.isCollected()) {
                BufferedImage cImg = testCoin.getImage() != null ? testCoin.getImage() : imageCoin;
                if (cImg != null) {
                    g2.drawImage(cImg, testCoin.getX() * tileSize, testCoin.getY() * tileSize, tileSize, tileSize, null);
                }
            }

            // Vẽ Door
            if (testDoor != null) {
                BufferedImage dImg = testDoor.getImage() != null ? testDoor.getImage() : imageDoor;
                if (dImg != null) {
                    g2.drawImage(dImg, testDoor.getX() * tileSize, testDoor.getY() * tileSize, tileSize, tileSize, null);
                }
            }

            // Vẽ Box
            BufferedImage bImg = (testBox != null && testBox.getImage() != null) ? testBox.getImage() : imageBox;
            if (bImg != null && testBox != null) {
                g2.drawImage(bImg, testBox.getX() * tileSize, testBox.getY() * tileSize, tileSize, tileSize, null);
            }

            // Vẽ các Teleporter
            if (teleManager != null) {
                List<Teleporter> list = teleManager.getTeleporters();
                for (int i = 0; i < list.size(); i++) {
                    Teleporter t = list.get(i);
                    BufferedImage img = (i % 2 == 0) ? imageTeleport : imageTeleport2;
                    if (img != null) {
                        g2.drawImage(img, t.getX() * tileSize, t.getY() * tileSize, tileSize, tileSize, null);
                    }
                }
            }

            // 3. Vẽ Player (quay theo hướng di chuyển)
            BufferedImage currentPlayerImg = imagePlayer;
            if (testPlayer != null && testPlayer.getDirection() != null) {
                switch (testPlayer.getDirection()) {
                    case UP: if (imagePlayerUp != null) currentPlayerImg = imagePlayerUp; break;
                    case DOWN: if (imagePlayerDown != null) currentPlayerImg = imagePlayerDown; break;
                    case LEFT: if (imagePlayerLeft != null) currentPlayerImg = imagePlayerLeft; break;
                    case RIGHT: if (imagePlayerRight != null) currentPlayerImg = imagePlayerRight; break;
                }
            }
            if (currentPlayerImg != null && testPlayer != null) {
                g2.drawImage(currentPlayerImg, testPlayer.getX() * tileSize, testPlayer.getY() * tileSize, tileSize, tileSize, null);
            }

            // 4. Vẽ UI (HUD, Pause, Win) đè lên trên cùng
            ui.draw(g2);
        }

        g2.dispose(); 
    }
}
