package Servicios;

import Implementacion.LibreriaDAOImpl;
import ORM.Libreria;
import Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.net.ConnectException;
import java.util.List;

public class LibreriaDataService {
    public LibreriaDataService(Session miSession) {this.miSession = miSession;}
    private Session miSession;
    LibreriaDAOImpl l = new LibreriaDAOImpl();
    public String Guardar(Libreria libreria) {
        l.create(libreria, miSession);
        return "Objeto añadido";
    }
}