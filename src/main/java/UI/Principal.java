package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    public JPanel panelMain;
    private JPanel panelMenu;
    private JTabbedPane tab;
    private JPanel principal;
    private JPanel top;
    private JButton btTop;
    private JButton btTipo;
    private JButton btHome;

    public Principal(){

        btHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(0);
            }
        });
        btTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(1);
            }
        });
    }
}
