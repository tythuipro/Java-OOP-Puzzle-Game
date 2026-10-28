package PuzzleGame.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Lớp đại diện cho đồng xu thưởng (Coin).
 * Kế thừa từ lớp cha trừu tượng GameObject (Áp dụng tính Kế thừa & Đóng gói).
 * Khi nhân vật di chuyển vào ô chứa Coin, người chơi sẽ thu thập đồng xu và được cộng điểm số (Score).
 */
public class Coin extends GameObject {
    private boolean collected;
    private int value; // Điểm số nhận được khi nhặt (mặc định 100 điểm)

    public Coin(int x, int y) {
        this(x, y, 100);
    }

    public Coin(int x, int y, int value) {
        super(x, y);
        this.collected = false;
        this.value = value;
        loadImage("assets/images/puzzle objects/coin.png");

        // Dự phòng tạo ảnh bằng Java2D nếu tệp hình ảnh không khả dụng
        if (this.image == null) {
            this.image = createFallbackCoinImage();
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

    public void reset() {
        this.collected = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Tạo hình ảnh đồng xu vàng chất lượng cao dự phòng bằng Java2D
     */
    private BufferedImage createFallbackCoinImage() {
        int size = 64;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Đổ bóng
        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(8, 12, 48, 48);

        // Vành ngoài vàng đậm
        GradientPaint rimGrad = new GradientPaint(10, 8, new Color(255, 220, 100), 54, 52, new Color(180, 120, 20));
        g2.setPaint(rimGrad);
        g2.fillOval(8, 8, 48, 48);
        g2.setColor(new Color(130, 80, 10));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(8, 8, 48, 48);

        // Mặt trong vàng sáng
        GradientPaint faceGrad = new GradientPaint(14, 14, new Color(255, 240, 140), 50, 50, new Color(220, 160, 30));
        g2.setPaint(faceGrad);
        g2.fillOval(14, 14, 36, 36);

        // Viền trong
        g2.setColor(new Color(190, 130, 25, 180));
        g2.drawOval(18, 18, 28, 28);

        // Ký hiệu chữ 'C' ở giữa
        g2.setColor(new Color(140, 85, 10));
        g2.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 22));
        g2.drawString("C", 25, 40);
        g2.setColor(new Color(255, 255, 220));
        g2.drawString("C", 24, 39);

        // Vệt sáng phản chiếu (Shine)
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawArc(12, 12, 40, 40, 110, 50);

        g2.dispose();
        return img;
    }
}
