import ORM.Libreria;
import Servicios.LibreriaDataService;
import UI.Principal;
import Util.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Principal login = new Principal();
        login.setContentPane(login.panelMain);
        login.setTitle("Login");
        login.setSize(960, 540);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
