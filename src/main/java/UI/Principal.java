package UI;

import ORM.Libreria;
import Servicios.LibreriaDataService;
import Util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Principal extends JFrame{
    private org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
    public JPanel panelMain;
    private JPanel panelMenu;
    private JTabbedPane tab;
    private JPanel home;
    private JPanel top;
    private JButton btTop;
    private JButton btTipo;
    private JButton btHome;
    private JScrollPane scrollPane;
    private JTable homeTable;
    private JTextField tfNombre;
    private JTextField tfTipo;
    private JTextField tfFecha;
    private JTextField tfPuntuacion;
    private JPanel pn1;
    private JPanel pn2;
    private JPanel pn3;
    private JPanel pn4;
    private JPanel pn5;
    private JPanel pn6;
    private JPanel pn7;
    private JPanel pn8;

    public Principal(){
        generarTabla();
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

    public void generarTabla(){
        LibreriaDataService l = new LibreriaDataService(session);
        ArrayList<Libreria> listaLibrerias = l.readAll();
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha-Fin");
        modeloTabla.addColumn("Puntuacion");
        for(int i = 0; i < 50; i++) {
            for (Libreria libreria : listaLibrerias) {
                Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getPuntuacion()};
                modeloTabla.addRow(fila);
            }
        }

        modeloTabla.fireTableDataChanged();
    }
}
