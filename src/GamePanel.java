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
    static final int UNIT_SIZE = 25;
    // Quantidade de objeto que podem caber dentro da nossa tela
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    static final int SCORE_SIZE = 40;
    static final int GAME_OVER_SIZE = 75;
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
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this); // this executa o action performed]
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            // Desenhar maçã
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Desenhar a cobra
            for (int i = 0; i < bodyParts; i++) {
                // Cabeça da cobra
                if (i == 0) {
                    g.setColor(Color.green);
                }
                // Corpo da cobra
                else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            showScore(g);
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        // O valor randômico vai de zero até o parâmetro colocado menos 1
        appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;

        int i = 0;
        while (i < bodyParts) {
            // A maçã só será mostrada na tela se a posição dela não for a do corpo da cobra
            if (x[i] == appleX && y[i] == appleY) {
                i = 0;
                appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
                appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
            } else {
                i++;
            }
        }
    }

    public void move() {
        System.out.println("move");
        // Muda a direção do corpo da cobra
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // Muda a posição da cabeça da cobra (Obs: não pode mudar para a direção inversa da direção atual)
        switch (direction) {
            //Cima
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            // Esquerda
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            // Direita
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            // Baixo
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {

        // Verifica se cabeça da cobra pegou maçã
        if (x[0] == appleX && y[0] == appleY) {
            applesEaten++;
            bodyParts++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Verifica se colidiu com o próprio corpo.
        // Precisa começar com bodyParts - 1 pois estamos, na parte de mover, movendo 1 bloco a mais do que o tamanho
        // normal da cobra. Nesse caso, se o tamanho da cobra é 6, então mostramos na tela as posições de 0 a 5.
        // Porém, estamos movendo da posição 0 até 6.
        // Portanto, aqui na parte de verificar colisões, precisamos verificar apenas a parte que é mostrada na tela,
        // ou seja, considerar apenas as posições de 0 a 5 ( 0 até bodyParts - 1)
        for (int i = bodyParts-1; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Verifica se colidiu com a borda direita
        if (x[0] == SCREEN_WIDTH) {
            running = false;
        }

        // Verifica se colidiu com a borda esquerdad
        if (x[0] < 0) {
            running = false;
        }

        // Verifica se colidiu com a borda inferior
        if (y[0] == SCREEN_HEIGHT) {
            running = false;
        }

        // Verifica se colidiu com borda superior
        if (y[0] < 0) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, GAME_OVER_SIZE));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH - metrics.stringWidth("Game over"))/2, SCREEN_HEIGHT / 2);

        showScore(g);
    }

    public void showScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, SCORE_SIZE));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, SCORE_SIZE);
    }

    // Será executado a cada DELAY segundos
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            System.out.println("X head: " + x[0]);
            System.out.println("Y head: " + y[0]);

            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if ( direction != 'D') {
                        direction = 'U';
                    }
                    break;

            }
        }
    }
}
