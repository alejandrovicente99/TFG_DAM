import UI.Login;
import javax.swing.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Login login = new Login();
        login.setContentPane(login.panelMain);
        login.setTitle("Login");
        login.setSize(350, 200);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        login.setResizable(false);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
