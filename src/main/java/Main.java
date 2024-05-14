import UI.Principal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Principal login = new Principal();
        login.setContentPane(login.panelMain);
        login.setTitle("Libreria");
        login.setSize(960, 540);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        login.setResizable(false);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
