package UI;

import ORM.Libreria;
import Servicios.LibreriaDataService;
import Util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Principal extends JFrame{
    private org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
    public JPanel panelMain;
    private JPanel panelMenu;
    private JTabbedPane tab;
    private JPanel home;
    private JButton btTop;
    private JButton btTipo;
    private JButton btHome;
    private JScrollPane scrollPane;
    private JTable homeTable;
    private JTextField tfSearch;
    private JComboBox cbSearch;
    private JPanel anyadir;
    private JButton btAnyadir;
    private JTextField tfAnyadirNombre;
    private JComboBox cbAnyadirTipo;
    private JComboBox cbAnyadirAnyo;
    private JComboBox cbAnyadirMes;
    private JComboBox cbAnyadirDia;
    private JTextField tfAnyadirPuntuacion;
    private JPanel pnNombre;
    private JPanel pnTipo;
    private JPanel pnFecha;
    private JPanel pnPuntuacion;
    private JTextField tfAnyadirEnlace;
    private JTable tbAnyadir;
    private JButton btAceptarAnyadir;

    public Principal(){
        LibreriaDataService l = new LibreriaDataService(session);
        ArrayList<Libreria> listaLibrerias = l.readAll();

        generarTabla(listaLibrerias);
        cargarComboBox();
        cargarcbSearch();

        btHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(0);
            }
        });
        btAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(1);
            }
        });
        btTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(2);
            }
        });
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    actualizarTabla(l.find(cbSearch.getSelectedItem().toString().trim(), tfSearch.getText().trim()));
                }
            }
        });
    }

    public void generarTabla(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Puntuacion");
        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getPuntuacion()};
            modeloTabla.addRow(fila);
        }
        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTabla(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getPuntuacion()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void cargarComboBox(){
        LibreriaDataService l = new LibreriaDataService(session);
        List<String> tipos = l.readTipos();

        //cbSearch.setModel(new DefaultComboBoxModel(tipos.toArray()));
    }
    public void cargarcbSearch(){
        cbSearch.removeAllItems();
        cbSearch.addItem("Nombre");
        cbSearch.addItem("Tipo");
        cbSearch.addItem("Fecha fin");
        cbSearch.addItem("Puntuacion");
    }
}
