/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DibujoTablero extends JPanel {

    private static final int CELL_SIZE = 40;
    private static final Color BACKGROUND_COLOR = new Color(214, 184, 154);

    public DibujoTablero() {
        setPreferredSize(new Dimension(625, 613));
        setBackground(BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawCentralDecoration(g2d);
        drawBoardArms(g2d);
        drawGreenCells(g2d);
        drawCornerTriangles(g2d);
        drawGamePieces(g2d);
        drawGreenBills(g2d);
    }

    private void drawCentralDecoration(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 80;

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));

        // Círculo exterior
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // Patrón decorativo
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            int x1 = (int) (centerX + radius * Math.cos(angle));
            int y1 = (int) (centerY + radius * Math.sin(angle));
            int x2 = (int) (centerX + (radius - 20) * Math.cos(angle));
            int y2 = (int) (centerY + (radius - 20) * Math.sin(angle));
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawBoardArms(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        // Los cuatro brazos
        drawArm(g2d, centerX - CELL_SIZE, centerY - 240, 2, 8);
        drawArm(g2d, centerX - CELL_SIZE, centerY + 80, 2, 8);
        drawArm(g2d, centerX - 240, centerY - CELL_SIZE, 8, 2);
        drawArm(g2d, centerX + 80, centerY - CELL_SIZE, 8, 2);
    }

    private void drawArm(Graphics2D g2d, int x, int y, int cols, int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g2d.drawRect(x + (j * CELL_SIZE), y + (i * CELL_SIZE), CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawGreenCells(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        Color lightGreen = new Color(144, 238, 144);

        g2d.setColor(lightGreen);

        g2d.fillRect(centerX - CELL_SIZE, centerY - CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g2d.fillRect(centerX, centerY - CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g2d.fillRect(centerX - CELL_SIZE, centerY, CELL_SIZE, CELL_SIZE);
        g2d.fillRect(centerX, centerY, CELL_SIZE, CELL_SIZE);
    }

    private void drawCornerTriangles(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.setColor(Color.DARK_GRAY);

        drawTriangle(g2d, centerX - CELL_SIZE, centerY - 240, CELL_SIZE * 2, CELL_SIZE);
        drawTriangle(g2d, centerX - CELL_SIZE, centerY + 240, CELL_SIZE * 2, CELL_SIZE);
        drawTriangle(g2d, centerX - 240, centerY - CELL_SIZE, CELL_SIZE, CELL_SIZE * 2);
        drawTriangle(g2d, centerX + 240 - CELL_SIZE, centerY - CELL_SIZE, CELL_SIZE, CELL_SIZE * 2);
    }

    private void drawTriangle(Graphics2D g2d, int x, int y, int width, int height) {
        int[] xPoints = {x, x + width / 2, x + width};
        int[] yPoints = {y, y + height, y};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawGamePieces(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        drawPiece(g2d, centerX - 160, centerY - 200, Color.ORANGE);
        drawPiece(g2d, centerX - 120, centerY - 160, Color.RED);
        drawPiece(g2d, centerX + 120, centerY - 160, Color.YELLOW);
        drawPiece(g2d, centerX + 160, centerY + 120, Color.MAGENTA);
    }

    private void drawPiece(Graphics2D g2d, int x, int y, Color color) {
        int pieceSize = 20;
        g2d.setColor(color);
        g2d.fillOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
    }

    private void drawGreenBills(Graphics2D g2d) {
        int topY = 50;
        int startX = getWidth() / 2 - 150;

        g2d.setColor(new Color(0, 150, 0));

        for (int i = 0; i < 5; i++) {
            g2d.fillRect(startX + (i * 70), topY, 50, 30);
        }
    }
}
