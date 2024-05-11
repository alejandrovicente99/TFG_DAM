package Servicios;

import Implementacion.LibreriaDAOImpl;
import ORM.Libreria;
import Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class LibreriaDataService {
    public LibreriaDataService(Session miSession) {this.miSession = miSession;}
    private Session miSession;
    LibreriaDAOImpl l = new LibreriaDAOImpl();
    public String Guardar(Libreria libreria) {
        if(libreria.getNombre() == null || libreria.getNombre().equals("")){
            return "El nombre no puede ser vacio";
        }
        if(libreria.getFechaFin() == null || libreria.getFechaFin().equals("")){
            return "El fecha de fin no puede ser vacio";
        }
        l.create(libreria, miSession);
        return "Objeto añadido";
    }
    public ArrayList<Libreria> readAll(){
        return l.readAll(miSession);
    }
    public List<String> readTipos(){
        return l.readTipos(miSession);
    }
    public ArrayList<Libreria> find(String cb, String tf){
        if(tf == null || tf.equals("")){
            return l.readAll(miSession);
        }
        return l.find(miSession, cb, tf);
    }
    public ArrayList<Libreria> findByType(String tipo){
        if(tipo == null || tipo.equals("")){
            return l.readAll(miSession);
        }
        return l.findByType(miSession, tipo);
    }
    public int ranking(String nombre){
        return l.ranking(nombre, miSession);
    }
    public String update(Libreria libreria){
        l.update(libreria, miSession);
        return "Hecho";
    }
}