package UI;

import ORM.Libreria;
import Scrap.Extract_metacritic;
import Scrap.Extract_imdb;
import Scrap.Scrap;
import Servicios.LibreriaDataService;
import Util.HibernateUtil;
import org.hibernate.Session;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Principal extends JFrame{
    private Session session = HibernateUtil.getSessionFactory().openSession();
    private LibreriaDataService l = new LibreriaDataService(session);
    private Scrap s = new Scrap();
    //private Individual i = new Individual(session);
    private Extract_imdb ei = new Extract_imdb();
    private Extract_metacritic em = new Extract_metacritic();


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
    private JTable tbAnyadir;
    private JButton btAceptarAnyadir;
    private JPanel Individual;
    private JPanel pnImagen;
    private JLabel laNombre;
    private JLabel laTipo;
    private JLabel laFecha;
    private JLabel laMetacritic;
    private JLabel laPuntuacion;
    private JLabel laRanking;
    private JLabel laAnyadir;

    public Principal(){
        //LibreriaDataService l = new LibreriaDataService(session);
        ArrayList<Libreria> listaLibrerias = l.readAll();

        //Inicio app
        generarTablaHome(listaLibrerias);
        generarTablaAnyadir();
        //No se usa -- cargarComboBox();
        cargarcbSearch();
        cargarCbFecha();
        cargarDia31();
        cargarCbAnyadirTipo();
        // no se usa -- cargarTodasImagenes();

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
                limpiarAnyadir();
                laAnyadir.setText("");
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
        homeTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) homeTable.getModel();
                    String nombre = (String) model.getValueAt(row, 0);
                    abrirIndividual(nombre);
                    tab.setSelectedIndex(2);
                }
            }
        });

        //pestaña añadir
        btAceptarAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre, tipo, anyo, mes, dia;
                double puntuacion = 0;

                laAnyadir.setText(null);

                nombre = tfAnyadirNombre.getText().trim();
                tipo = cbAnyadirTipo.getSelectedItem().toString().trim();
                anyo = cbAnyadirAnyo.getSelectedItem().toString().trim();
                mes = cbAnyadirMes.getSelectedItem().toString().trim();
                dia = cbAnyadirDia.getSelectedItem().toString().trim();

                if(tfAnyadirPuntuacion.getText().trim()==null || tfAnyadirPuntuacion.getText().trim().equals("")){
                    laAnyadir.setText("La puntuacion no puede estar vacia");
                }else {
                    try {
                        puntuacion = Double.parseDouble(tfAnyadirPuntuacion.getText().trim());

                        if(puntuacion <= 10 || puntuacion >= 0) {
                            laAnyadir.setText(anyadirDatos(nombre, tipo, anyo, mes, dia, puntuacion));
                            limpiarAnyadir();
                            actualizarTablaAnyadir(l.findByType(cbAnyadirTipo.getSelectedItem().toString().trim()));
                        }else{
                            laAnyadir.setText("La puntuacion debe ser mayor que 0 y menor que 10");
                        }
                    } catch (NumberFormatException ex){
                        laAnyadir.setText("La puntuacion debe ser un valor numerico, y los decimales se ponen con un punto (.)");
                        tfAnyadirPuntuacion.setText(null);
                    }
                }
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
        homeTable.removeAll();
        DefaultTableModel modeloTabla = new ReadOnlyTableModel();
        homeTable.setModel(modeloTabla);

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Puntuacion");
        modeloTabla.addColumn("IMDB/Metacritic");

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void generarTablaAnyadir(){
        tbAnyadir.removeAll();
        DefaultTableModel modeloTabla = new ReadOnlyTableModel();
        tbAnyadir.setModel(modeloTabla);

        modeloTabla.setRowCount(0);

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Puntuacion");
        modeloTabla.addColumn("IMDB/Metacritic");

        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTablaHome(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTablaAnyadir(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) tbAnyadir.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), libreria.getFechaFin(), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    /*public void cargarComboBox(){
        //LibreriaDataService l = new LibreriaDataService(session);
        List<String> tipos = l.readTipos();

        //cbSearch.setModel(new DefaultComboBoxModel(tipos.toArray()));
    }*/
    public void cargarcbSearch(){
        cbSearch.removeAllItems();
        cbSearch.addItem("Nombre");
        cbSearch.addItem("Tipo");
        cbSearch.addItem("Fecha fin");
        cbSearch.addItem("Puntuacion");
    }
    public String anyadirDatos(String nombre, String tipo, String anyo, String mes, String dia, double puntuacion){
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
        String puntuacionImdbMetacritic = "";
        String imagen = "";

        if(tipo.equals("Videojuego")){
            puntuacionImdbMetacritic = "Metacritic : " + em.puntuacionMetacritic(nombre);

        }else{
            puntuacionImdbMetacritic = "IMDB : " + ei.puntuacionIMDB(nombre);
            imagen = ei.imagenImdb2(nombre);
        }

        Libreria libreria = new Libreria(nombre, tipo, fecha, puntuacion, puntuacionImdbMetacritic, imagen);

        return l.Guardar(libreria);
    }
    public void cargarCbAnyadirTipo(){
        cbAnyadirTipo.removeAllItems();
        List<String> tipos = l.readTipos();

        cbAnyadirTipo.setModel(new DefaultComboBoxModel(tipos.toArray()));
    }
    public void cargarCbFecha(){
        cbAnyadirAnyo.removeAllItems();
        cbAnyadirMes.removeAllItems();

        for(int i = 2024; i >= 2000; i--){
            cbAnyadirAnyo.addItem(i);
        }
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbAnyadirMes.setModel(new DefaultComboBoxModel(meses));
    }
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
    public void cargarDia30(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 31; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    public void cargarDia31(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 32; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    public void cargarDiaFebrero(){
        cbAnyadirDia.removeAllItems();
        for(int i = 1; i < 29; i++){
            cbAnyadirDia.addItem(i);
        }
    }
    public void limpiarAnyadir(){
        tfAnyadirNombre.setText("");
        tfAnyadirPuntuacion.setText("");
    }
    public void abrirIndividual(String nombre){
        ArrayList<Libreria> dato = l.find("Nombre", nombre);
        Libreria registro = dato.get(0);
        laNombre.setText("Nombre : " + registro.getNombre());
        laPuntuacion.setText("Puntuacion : " + registro.getPuntuacion());
        laFecha.setText("Fecha : " + registro.getFechaFin());
        laPuntuacion.setText("Puntuacion : " + registro.getPuntuacion());
        laMetacritic.setText("Puntuacion en " + registro.getImdbMetacritic());
        laTipo.setText("Tipo : " + registro.getTipo());
        laRanking.setText("Ranking : " + l.ranking(registro));
        cargarImagen(registro.getImagen());
    }
    public void cargarImagen(String imagen){
        pnImagen.removeAll();

        Image image = null;
        URL url = null;
        try {
            url = new URL(imagen);
            image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel laImagen = new JLabel(icon);
            pnImagen.add(laImagen);
            pnImagen.setAlignmentX(0);
            pnImagen.setAlignmentY(0);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL");
        } catch (IOException iox) {
            System.out.println("Can not load file");
        }

    }
    //No se usa --
    /*public void cargarTodasImagenes(){
        ArrayList<Libreria> libreria = l.readAll();
        String imagen = "";
        for(Libreria lib : libreria){
            imagen = "";

            if(lib.getTipo().equals("Videojuego")){

            }else{
                imagen = ei.imagenImdb2(lib.getNombre());
            }

            lib.setImagen(imagen);
            l.update(lib);
        }
    }*/
    class ReadOnlyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Todas las celdas son de solo lectura
        }
    }
}