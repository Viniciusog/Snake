import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // Tamanho dos objetos no nosso jogo
    static final int UNIT_SIZE = 50;
    // Quantidade de objeto que podem caber dentro da nossa tela
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    // A nossa cobra pode ocupar todos os quadrados da tela, portanto, o tamanho máximo da cobra
    // é o valor máximo de quadrados
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R'; // L, R, U, D
    boolean running = false;
    Timer timer;
    Random random;



    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        timer.start();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }

    public void newApple() {
        // O valor randômico vai de zero até o parâmetro colocado menos 1
        int randomicoX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE);
        appleX = randomicoX * UNIT_SIZE;
        int randomicoY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE);
        appleY = randomicoY * UNIT_SIZE;

        System.out.println("Randômico X: " + randomicoX);
        System.out.println("Randômico Y: " + randomicoY);
        System.out.println("Apple X: " + appleX);
        System.out.println("Apple Y: " + appleY);
        System.out.println("");
    }

    public void move() {

    }

    public void checkApple() {

    }

    public void checkCollisions() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }
    }
}
