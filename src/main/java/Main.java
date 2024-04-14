import ORM.Libreria;
import Servicios.LibreriaDataService;
import Util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Session miSession = HibernateUtil.getSessionFactory().openSession();
        LibreriaDataService l = new LibreriaDataService(miSession);
        Libreria lib = new Libreria("Hollow Knight", "Videojuego","2018-08-09", 10);
        l.Guardar(lib);
    }
}
