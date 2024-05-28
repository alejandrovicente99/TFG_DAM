package UI;

import ORM.Libreria;
import Scrap.Extract_videogame;
import Scrap.Extract_imdb;
import Servicios.LibreriaDataService;
import Util.HibernateUtil;

import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Principal extends JFrame{
    private final Session session = HibernateUtil.getSessionFactory().openSession();
    private final LibreriaDataService l = new LibreriaDataService(session);
    private final Extract_imdb ei = new Extract_imdb();
    private final Extract_videogame em = new Extract_videogame();

    public JPanel panelMain;
    private JPanel panelMenu;
    private JTabbedPane tab;
    private JPanel home;
    private JButton btTop;
    private JButton btHome;
    private JScrollPane scrollPane;
    private JTable homeTable;
    private JTextField tfSearch;
    private JComboBox cbSearch;
    private JPanel anyadir;
    private JButton btAnyadir;
    private JTextField tfAnyadirNombre;
    private JComboBox cbAnyadirTipo;
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
    private JComboBox cbEditarTipo;
    private JTextField tfPuntuacion;
    private JTextField tfEditarNombre;
    private JButton btEditar;
    private JButton btAceptar;
    private JButton btCancelar;
    private JLabel laUpdate;
    private JButton btEliminar;
    private JLabel la1;
    private JLabel la2;
    private JLabel la6;
    private JLabel la7;
    private JLabel la8;
    private JButton btCargarImagen;
    private JPanel pnCalendarioIndividual;
    private JPanel pnCalendarioAnyadir;

    private String nombreID;
    private Libreria libID = new Libreria();
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private boolean conexion = true;

    public Principal() {
        //Cargar calendarios
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JDatePanelImpl datePanel1 = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());

        pnCalendarioAnyadir.add(datePicker);
        pnCalendarioIndividual.add(datePicker1);

        //Inicio app
        generarTablaHome(l.readAll());
        generarTablaAnyadir();
        cargarcbSearch();
        cargarCbAnyadirTipo(cbAnyadirTipo);

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
                model.setValue(null);
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
        //doble click
        homeTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) homeTable.getModel();
                    String nombre = (String) model.getValueAt(row, 0);
                    abrirIndividual(nombre);
                    btEditar.setVisible(true);
                    btEliminar.setVisible(true);
                    btAceptar.setVisible(false);
                    btCancelar.setVisible(false);
                    libID = session.get(Libreria.class, nombre);
                    nombreID = nombre;
                    System.out.println(nombre);
                    laUpdate.setVisible(false);
                    tab.setSelectedIndex(2);
                }
            }
        });

        //pestaña añadir
        btAceptarAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre, tipo, laAnyadirTEXT;
                double puntuacion = 0;

                laAnyadir.setText(null);

                nombre = tfAnyadirNombre.getText().trim();
                tipo = cbAnyadirTipo.getSelectedItem().toString().trim();
                Date selectedDate = (Date) datePicker1.getModel().getValue();

                if(tfAnyadirPuntuacion.getText().trim()==null || tfAnyadirPuntuacion.getText().trim().equals("")){
                    laAnyadir.setForeground(Color.red);
                    laAnyadir.setText("La puntuacion no puede estar vacia");
                }else {
                    try {
                        puntuacion = Double.parseDouble(tfAnyadirPuntuacion.getText().trim());

                        if(puntuacion <= 10 || puntuacion >= 0) {
                            laAnyadirTEXT = l.Guardar(new Libreria(nombre, tipo, selectedDate, puntuacion, null, null));
                            laAnyadir.setText(laAnyadirTEXT);
                            if(laAnyadirTEXT.equals("Registro ingresado en BBDD")){
                                laAnyadir.setForeground(Color.green);
                            }else{
                                laAnyadir.setForeground(Color.red);
                            }
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
        cbAnyadirTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaAnyadir(l.findByType(cbAnyadirTipo.getSelectedItem().toString().trim()));
            }
        });

        //pestaña individual
        btEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btCancelar.setVisible(true);
                btAceptar.setVisible(true);
                btEditar.setVisible(false);
                btCargarImagen.setVisible(false);
                btEliminar.setVisible(false);
                laUpdate.setVisible(false);

                laFecha.setText("Fecha : ");
                laTipo.setText("Tipo : ");
                laPuntuacion.setText("Puntuacion : ");
                laNombre.setText("Nombre : ");

                tfEditarNombre.setText(libID.getNombre());
                tfPuntuacion.setText(String.valueOf(libID.getPuntuacion()));

                model.setValue(libID.getFechaFin());

                tfEditarNombre.setVisible(true);
                tfPuntuacion.setVisible(true);

                pnCalendarioIndividual.setVisible(true);
                cbEditarTipo.setVisible(true);
                cargarCbAnyadirTipo(cbEditarTipo);
            }
        });
        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btCancelar.setVisible(false);
                btAceptar.setVisible(false);
                btCargarImagen.setVisible(true);
                btEditar.setVisible(true);
                btEliminar.setVisible(true);
                laUpdate.setVisible(false);

                abrirIndividual(nombreID);
            }
        });
        btAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String update = "";
                laUpdate.setVisible(true);

                String nombre = tfEditarNombre.getText().trim();
                String tipo = cbEditarTipo.getSelectedItem().toString().trim();
                Date fecha = (Date) datePicker.getModel().getValue();

                double puntuacion = Double.parseDouble(tfPuntuacion.getText().toString().trim());

                String puntuacionMetacritic = "";
                String imagen = "";

                Libreria libNEW = session.get(Libreria.class, nombre);

                if(nombre.equals(nombreID)){
                    Libreria libUpdate = new Libreria(nombre, tipo, fecha, puntuacion, libID.getImdbMetacritic(), libID.getImagen());
                    update = l.update(libUpdate);
                    laUpdate.setText(update);
                    abrirIndividual(libUpdate.getNombre());
                } else {
                    if(libNEW != null){
                        laUpdate.setText("Ya existe un registro con ese nombre");
                        abrirIndividual(nombreID);
                    } else {
                        l.delete(libID);
                        if (tipo.equals("Videojuego")) {
                            puntuacionMetacritic = "Metacritic : " + em.puntuacionMetacritic(nombre);
                            imagen = em.imagenSteamDB(nombre);
                            if(imagen.endsWith(".webm")) imagen = ei.imagenImdb2(nombre);
                        } else {
                            puntuacionMetacritic = "IMDB : " + ei.puntuacionIMDB(nombre);
                            imagen = ei.imagenImdb2(nombre);
                        }

                        Libreria libUpdate = new Libreria(nombre, tipo, fecha, puntuacion, puntuacionMetacritic, imagen);
                        update = update + l.update(libUpdate);
                        laUpdate.setText(update);
                        abrirIndividual(libUpdate.getNombre());
                    }
                }
            }
        });
        btEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    l.delete(libID);
                    actualizarTablaHome(l.readAll());
                    tab.setSelectedIndex(0);
                }
            }
        });
        btCargarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Libreria libNew = session.get(Libreria.class, nombreID);
                String imagen;
                String puntuacion;
                if(libNew.getTipo().equals("Videojuego")){
                    imagen = em.imagenSteamDB(nombreID);
                    puntuacion = "Metacritic : " + em.puntuacionMetacritic(nombreID);
                    if(imagen.endsWith(".webm")) imagen = ei.imagenImdb2(nombreID);
                }else{
                    imagen = ei.imagenImdb2(nombreID);
                    puntuacion = "IMDB : " + ei.puntuacionIMDB(nombreID);
                }

                libNew.setImagen(imagen);
                libNew.setImdbMetacritic(puntuacion);
                l.update(libNew);
                abrirIndividual(libNew.getNombre());
            }
        });

        //Hilo para comprobar conexion con BBDD
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (conexion) {
                    try {
                        Connection miSession = DriverManager.getConnection("jdbc:mysql://localhost:3306/Libreria?serverTimezone=Europe/Madrid","root", "");

                        if (miSession != null) {
                            miSession.close();
                            panelMain.setEnabled(true);
                        } else {
                            System.out.println("Espera");
                            Thread.sleep(1000);
                            continue;
                        }
                    }catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }catch (NullPointerException e){
                        continue;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error de conexion con la base de datos");
                        panelMain.setEnabled(false);
                        continue;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    //Generar tablas
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
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), formatter.format(libreria.getFechaFin()), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
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


    //Actualizar datos
    public void actualizarTablaHome(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) homeTable.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), formatter.format(libreria.getFechaFin()), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }
    public void actualizarTablaAnyadir(ArrayList<Libreria> listaLibrerias){
        DefaultTableModel modeloTabla = (DefaultTableModel) tbAnyadir.getModel();

        modeloTabla.setRowCount(0);

        for (Libreria libreria : listaLibrerias) {
            Object[] fila = {libreria.getNombre(), libreria.getTipo(), formatter.format(libreria.getFechaFin()), libreria.getPuntuacion(), libreria.getImdbMetacritic()};
            modeloTabla.addRow(fila);
        }

        modeloTabla.fireTableDataChanged();
    }

    //Cargar datos
    public void cargarcbSearch(){
        cbSearch.removeAllItems();
        cbSearch.addItem("Nombre");
        cbSearch.addItem("Tipo");
        cbSearch.addItem("Fecha fin");
        cbSearch.addItem("Puntuacion");
    }
    public void cargarCbAnyadirTipo(JComboBox model){
        model.removeAllItems();
        model.addItem("Videojuego");
        model.addItem("Pelicula");
        model.addItem("Serie");
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

            Border bordeBlanco = BorderFactory.createLineBorder(Color.WHITE, 5);
            Border bordeExistente = laImagen.getBorder();
            Border bordeCompuesto = new CompoundBorder(bordeExistente, bordeBlanco);
            laImagen.setBorder(bordeCompuesto);

            pnImagen.add(laImagen);
            pnImagen.setAlignmentX(0);
            pnImagen.setAlignmentY(0);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL");
        } catch (IOException iox) {
            System.out.println("Can not load file");
        }
    }

    public void limpiarAnyadir(){
        tfAnyadirNombre.setText("");
        tfAnyadirPuntuacion.setText("");
    }

    public void abrirIndividual(String nombre){
        Libreria registro = session.get(Libreria.class, nombre);

        laNombre.setText("Nombre : " + registro.getNombre());
        laPuntuacion.setText("Puntuacion : " + registro.getPuntuacion());
        laFecha.setText("Fecha : " + formatter.format(registro.getFechaFin()));
        laMetacritic.setText("Puntuacion en " + registro.getImdbMetacritic());
        laTipo.setText("Tipo : " + registro.getTipo());
        laRanking.setText("Ranking : " + l.ranking(registro));
        cargarImagen(registro.getImagen());

        laNombre.setVisible(true);
        laPuntuacion.setVisible(true);

        tfEditarNombre.setVisible(false);
        tfPuntuacion.setVisible(false);

        cbEditarTipo.setVisible(false);
        pnCalendarioIndividual.setVisible(false);

        btCancelar.setVisible(false);
        btAceptar.setVisible(false);
        btEditar.setVisible(true);
        btEliminar.setVisible(true);
        btCargarImagen.setVisible(true);
    }

    static class ReadOnlyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Todas las celdas son de solo lectura
        }
    }
    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}