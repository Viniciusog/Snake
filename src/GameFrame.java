import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // Frame fica com o tamanho de seu conteúdo[
        this.setVisible(true);
        this.setLocationRelativeTo(null);


        GamePanel gp = new GamePanel();

    }
}
