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
    private LibreriaDataService l = new LibreriaDataService(session);
    private Scrap.Scrap s = new Scrap.Scrap();
    private Individual i = new Individual(session);


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
    private JPanel Individual;
    private JLabel laImagen;
    private JPanel pnImagen;
    private JLabel laNombre;
    private JLabel laTipo;
    private JLabel laFecha;
    private JLabel laRanking;
    private JLabel laMetacritic;
    private JLabel laPuntuacion;

    public Principal(){
        //LibreriaDataService l = new LibreriaDataService(session);
        ArrayList<Libreria> listaLibrerias = l.readAll();

        generarTablaHome(listaLibrerias);
        generarTablaAnyadir();
        cargarComboBox();
        cargarcbSearch();
        cargarCbFecha();
        cargarDia31();
        cargarCbAnyadirTipo();


        //boton abrir pestañas
        btHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(0);
                actualizarTablaHome(l.readAll());
            }
        });
        btAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(1);
                actualizarTablaAnyadir(l.findByType(cbAnyadirTipo.getSelectedItem().toString()));
            }
        });
        btTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(2);
            }
        });


        //pestaña home
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    actualizarTablaHome(l.find(cbSearch.getSelectedItem().toString().trim(), tfSearch.getText().trim()));
                }
            }
        });

        //pestaña añadir
        btAceptarAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre, tipo, anyo, mes, dia, puntuacion, enlace;

                nombre = tfAnyadirNombre.getText().trim();
                tipo = cbAnyadirTipo.getSelectedItem().toString().trim();
                anyo = cbAnyadirAnyo.getSelectedItem().toString().trim();
                mes = cbAnyadirMes.getSelectedItem().toString().trim();
                dia = cbAnyadirDia.getSelectedItem().toString().trim();
                puntuacion = tfAnyadirPuntuacion.getText().trim();
                enlace = tfAnyadirEnlace.getText().trim();

                anyadirDatos(nombre, tipo, anyo, mes, dia, puntuacion, enlace);
                actualizarTablaAnyadir(l.readAll());
            }
        });
        cbAnyadirMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCbAnyadirDia(cbAnyadirMes.getSelectedItem().toString().trim());
            }
        });
        cbAnyadirTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaAnyadir(l.findByType(cbAnyadirTipo.getSelectedItem().toString().trim()));
            }
        });
    }

    public void generarTablaHome(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Puntuacion");

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion()};
            modeloTabla.addRow(fila);
        }
        modeloTabla.fireTableDataChanged();
    }
    public void generarTablaAnyadir(){
        DefaultTableModel modeloTabla = (DefaultTableModel) tbAnyadir.getModel();

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Puntuacion");

        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTablaHome(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getPuntuacion()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTablaAnyadir(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) tbAnyadir.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getPuntuacion()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void cargarComboBox(){
        //LibreriaDataService l = new LibreriaDataService(session);
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
    //añadir datos a la bbdd
    public String anyadirDatos(String nombre, String tipo, String anyo, String mes, String dia, String puntuacion, String enlace){
        if(nombre==null || nombre.isEmpty()){
            return "El nombre no puede estar vacio";
        }
        if(tipo==null || tipo.isEmpty()){
            return "El tipo no puede estar vacio";
        }
        if(anyo==null || anyo.isEmpty()){
            return "El año no puede estar vacio";
        }
        if(mes==null || mes.isEmpty()){
            return "El mes no puede estar vacio";
        }
        if(dia==null || dia.isEmpty()){
            return "El dia no puede estar vacio";
        }
        if(puntuacion==null || puntuacion.isEmpty()){
            return "La puntuacion no puede estar vacio";
        }
        if(Integer.parseInt(puntuacion)<0 || Integer.parseInt(puntuacion)>10){
            return "La puntuacion no puede ser ni mayor que 10 ni menor que 0";
        }
        int mesnum = 0;
        switch(mes){
            case "Enero": mesnum = 1; break;
            case "Febrero": mesnum = 2; break;
            case "Marzo": mesnum = 3; break;
            case "Abril": mesnum = 4; break;
            case "Mayo": mesnum = 5; break;
            case "Junio": mesnum = 6; break;
            case "Julio": mesnum = 7; break;
            case "Agosto": mesnum = 8; break;
            case "Septiembre": mesnum = 9; break;
            case "Octubre": mesnum = 10; break;
            case "Noviembre": mesnum = 11; break;
            case "Diciembre": mesnum = 12; break;
        }

        String fecha = anyo + "-" + mesnum + "-" + dia;

        Libreria libreria = new Libreria(nombre, tipo, fecha, Integer.parseInt(puntuacion), enlace);

        return l.Guardar(libreria);
    }
    //cargar combobox de tipo de anyadir
    public void cargarCbAnyadirTipo(){
        cbAnyadirTipo.removeAllItems();
        List<String> tipos = l.readTipos();

        cbAnyadirTipo.setModel(new DefaultComboBoxModel(tipos.toArray()));
    }
    //cargar comboboxes de la fecha
    public void cargarCbFecha(){
        cbAnyadirAnyo.removeAllItems();
        cbAnyadirMes.removeAllItems();

        for(int i = 2024; i >= 2000; i--){
            cbAnyadirAnyo.addItem(i);
        }
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbAnyadirMes.setModel(new DefaultComboBoxModel(meses));
    }
    //Cargar cb dia
    public void cargarCbAnyadirDia(String mes){
        switch(mes){
            case "Enero": cargarDia31(); break;
            case "Febrero": cargarDiaFebrero(); break;
            case "Marzo": cargarDia31(); break;
            case "Abril": cargarDia30(); break;
            case "Mayo": cargarDia31(); break;
            case "Junio": cargarDia30(); break;
            case "Julio": cargarDia31(); break;
            case "Agosto": cargarDia31(); break;
            case "Septiembre": cargarDia30(); break;
            case "Octubre": cargarDia31(); break;
            case "Noviembre": cargarDia30(); break;
            case "Diciembre": cargarDia31(); break;
        }
    }
    //Cargar Dias pares
    public void cargarDia30(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 31; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    //Cargar Dias impares
    public void cargarDia31(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 32; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    //Cargar febrero
    public void cargarDiaFebrero(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 29; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    //Scrapping
    public String puntuacionMetacritic(String url){
        return s.webScrap(url).get(0);
    }
}
