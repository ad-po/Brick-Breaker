import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    //wymiary okna gry
    private int windowWidth = 600;
    private int windowHeight = 500;

    //wymiary paletki
    private int platformWidth = 100;
    private int platformHeight = 10;
    private int xp = (windowWidth - platformWidth) / 2;
    private int yp = windowHeight - platformHeight;

    //wymiary pilki
    private int ballDiameter = 20;
    private int ballX = (windowWidth - ballDiameter) / 2;
    private int ballY = (windowHeight - ballDiameter) / 2;

    //cegly
    private Random random = new Random();
    private int row = random.nextInt(6) + 5;
    private int column = random.nextInt(6) + 5;


    //wymiar cegly
    private int brickWidth = 50;
    private int brickHeight = 10;
    private int brickSpace = 5;

    private int brickSpaceL = (windowWidth - ((brickWidth * column) + (brickSpace * (column)-5))) / 2;



    private int bricksX[][] = new int[column][row];
    private int bricksY[][] = new int[column][row];



    private boolean isBroken[][] = new boolean[column][row];
    //pilka szybkosc
    private int xpp = 3;
    private int ypp = -3;

    private Timer timer;



    public GamePanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        //ustawienie cegiel
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                bricksX[i][j] = i * (brickWidth + brickSpace) + brickSpaceL;
                bricksY[i][j] = j * (brickHeight + brickSpace) + brickSpace;
                isBroken[i][j] = false;
            }
        }
        timer = new Timer(8, this);
        timer.start();
        addKeyListener(new KeyPressed());
        setFocusable(true);


    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlatform(g);
        drawBall(g);
        drawBricks(g);
        gameStatus(g);
    }






    private void drawPlatform(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xp, yp, platformWidth, platformHeight);
        g.setColor(Color.white);
        g.drawRect(xp, yp, platformWidth, platformHeight);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
        g.setColor(Color.white);
        g.drawOval(ballX, ballY, ballDiameter, ballDiameter);
    }

    private void drawBricks(Graphics g) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (isBroken[i][j] == false) {
                    g.setColor(Color.green);
                    g.fillRect(bricksX[i][j], bricksY[i][j], brickWidth, brickHeight);
                    g.setColor(Color.white);
                    g.drawRect(bricksX[i][j], bricksY[i][j], brickWidth, brickHeight);
                }
            }
        }
    }






    private void ballControll() {
        if (ballX <= 0 || ballX >= windowWidth - ballDiameter) {
            xpp = -xpp;
        }
        if (ballY <= 0 || ballY + ballDiameter >= yp && ballX >= xp && ballX <= xp + platformWidth) {
            ypp = -ypp;
        }

        ballX += xpp;
        ballY += ypp;
    }




    private void collision() {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (isBroken[i][j] == false) {
                    //gora/dol
                    if (ballX >= bricksX[i][j] &&
                            ballX <= bricksX[i][j] + brickWidth &&
                            ballY + ballDiameter >= bricksY[i][j] &&
                            ballY <= bricksY[i][j] + brickHeight) {
                        ypp = -ypp;
                        isBroken[i][j] = true;
                        //bok
                    } else if (ballY >= bricksY[i][j] &&
                            ballY <= bricksY[i][j] + brickHeight &&
                            ballX + ballDiameter >= bricksX[i][j] &&
                            ballX <= bricksX[i][j] + brickWidth) {
                        xpp = -xpp;
                        isBroken[i][j] = true;
                    }
                }
            }
        }
    }







    private void gameStatus(Graphics g) {
        int count = 0;
        if (ballY > windowHeight) {
            g.setColor(Color.black);
            g.fillRect(0, 0, windowWidth, windowHeight);
            g.setColor(Color.white);
            g.drawString("GAME OVER", (windowWidth) / 2, windowHeight / 2);
            timer.stop();

        }
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (isBroken[i][j] == false) {
                    count += 1;
                }
            }
        }
        if (count == 0) {
            g.setColor(Color.black);
            g.fillRect(0, 0, windowWidth, windowHeight);
            g.setColor(Color.white);
            g.drawString("YOU WIN", (windowWidth) / 2, windowHeight / 2);
            timer.stop();


        }



    }





    @Override
    public void actionPerformed (ActionEvent e){
        collision();
        ballControll();
        repaint();
    }


    private class KeyPressed extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A && xp > 0){
                xp-=10;
            }
            if (key == KeyEvent.VK_D && xp < windowWidth - platformWidth){
                xp+=10;
            }


        }
    }




}



