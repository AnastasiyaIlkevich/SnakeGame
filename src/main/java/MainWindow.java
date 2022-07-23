import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake Game"); //Это то что будет показано сверху окошка
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// окончание работы (красный крестик)
        // размер поля
        setSize(320,345);// в пикселях
        setLocation(400,400); // располежение игры при запуске
        add(new GameField());
        setVisible(true); // видимость экрана
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
