import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {//Panel- элемент экрана
    private final int SIZE = 320; //высота и ширина матрицы
    private final int DOT_SIZE = 16;// размерность яблочка или звена змеи
    //320*320/16/16 = ALL_DOTS
    private final int ALL_DOTS = 400;// количество возможных точек

    //переменные хранения картинок
    private Image dot;
    private Image apple;

    //координаты
    private int[] x = new int[ALL_DOTS]; //x координаты
    private int[] y = new int[ALL_DOTS]; //y координаты

    //координаты яблок
    private int appleX; //x координаты
    private int appleY; //x координаты

    private int dots; // количество звеньев змеи, количество очков;
    private Timer timer; // нужен для установки количества кадров в секунду. Для ускорения змейки

    private boolean inGame = true;

    private List<Integer> listAppleX = new ArrayList<>();
    private List<Integer> listAppleY = new ArrayList<>();
    boolean ifContains;

    //кнопки
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    //метод подгружающий картинки
    public void loadImage() {
        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();
        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();
    }

    public void createApple() {
        Random random = new Random();
        ifContains = false;
        while (listAppleX.size() != 3) {
            appleX = random.nextInt(20) * DOT_SIZE;
            appleY = random.nextInt(20) * DOT_SIZE;
            ifContains = listAppleX.contains(appleX) && listAppleY.contains(appleY);
            if (!ifContains) {
                listAppleX.add(appleX);
                listAppleY.add(appleY);
            }
        }
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 48;
            x[i] = 48 - i * DOT_SIZE;
        }
        timer = new Timer(150, this);
        timer.start();
        createApple();
    }

    public void checkApple() {
        ifContains = false;
        for (int i = 0; i < listAppleX.size(); i++) {
            if (!ifContains) {
                ifContains = listAppleX.get(i) == x[0] && listAppleY.get(i) == y[0];
            }
        }
        if (ifContains) {
            dots++;
            listAppleX.remove((Integer) x[0]);
            listAppleY.remove((Integer) y[0]);
            createApple();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (inGame) {
            for (int i = 0; i < listAppleX.size(); i++) {
                graphics.drawImage(apple, listAppleX.get(i), listAppleY.get(i), this);
            }
            for (int i = 0; i < dots; i++) {
                graphics.drawImage(dot, x[i], y[i], this);//this - значит отрисовку проводить здесь(в этом классе
            }
        } else {
            String gameOver = "gameOver";
            graphics.setColor(Color.RED);
            graphics.drawString(gameOver, SIZE / 6, SIZE / 2);
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
            if (x[0] > SIZE)
                x[0] = 0;
            if (y[0] > SIZE)
                y[0] = 0;
            if (x[0] < 0)
                x[0] = SIZE;
            if (y[0] < 0)
                y[0] = SIZE;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;

    }

    public GameField(){
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }

    class FiledKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            super.keyPressed(keyEvent);
            int key = keyEvent.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }

            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }
        }

    }
}
