import javax.swing.*;

public class Window {

    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        JFrame frame = new JFrame("Brick Breaker");



        //ustawienia okna
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        frame.add(gamePanel);

        frame.pack();


    }
}
