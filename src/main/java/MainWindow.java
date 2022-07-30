import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake Game"); //Это то что будет показано сверху окошка
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// окончание работы (красный крестик)
        // размер поля
        setSize(350, 380);
        setLocation(800, 400);
        add(new GameField());
        setVisible(true); // видимость экрана
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
