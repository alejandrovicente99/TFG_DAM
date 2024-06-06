package UI;

import Util.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame{
    public JPanel panelMain;

    private String user = "root";
    private String pass = "root";
    private JButton btLogin;
    private JTextField tfUser;
    private JPasswordField tfPassword;

    public Login(){

        btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tfUser.getText().equals(user)) {
                        if (tfPassword.getText().equals(pass)) {
                            Principal principal = new Principal();
                            principal.setContentPane(principal.panelMain);
                            principal.setTitle("Media Tracker");
                            principal.setSize(960, 540);
                            principal.setLocationRelativeTo(null);
                            principal.setVisible(true);
                            principal.setResizable(false);
                            principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(btLogin, "Contraseña incorrecta");
                        }
                    } else {
                        JOptionPane.showMessageDialog(btLogin, "El usuario no existe");
                    }
                }catch (ExceptionInInitializerError ex){
                    JOptionPane.showMessageDialog(Login.this, "Error al conectarse a la BBDD", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            }
        });
        tfUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    tfPassword.requestFocus();
                }
            }
        });
        tfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    btLogin.doClick();
                }
            }
        });
    }
}
