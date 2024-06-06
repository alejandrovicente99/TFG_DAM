package Servicios;

import Implementacion.LibreriaDAOImpl;
import ORM.Libreria;
import Scrap.Extract_imdb;
import Scrap.Extract_videogame;
import org.hibernate.Session;
import java.util.ArrayList;

public class LibreriaDataService {
    public LibreriaDataService(Session miSession) {this.miSession = miSession;}

    private Session miSession;

    private final Extract_imdb ei = new Extract_imdb();
    private final Extract_videogame em = new Extract_videogame();

    LibreriaDAOImpl l = new LibreriaDAOImpl();

    public String Guardar(Libreria libreria) {
        if(libreria.getNombre()==null || libreria.getNombre().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        Libreria duplicado = miSession.get(Libreria.class, libreria.getNombre());
        if(duplicado!=null){
            return "Ya existe un registro con ese nombre";
        }
        if(libreria.getTipo()==null || libreria.getTipo().isEmpty()){
            return "El tipo no puede estar vacío";
        }
        if(libreria.getFechaFin() == null){
            return "La fecha no puede estar vacía";
        }

        if(libreria.getTipo().equals("Videojuego")){
            libreria.setImdbMetacritic("Metacritic : " + em.puntuacionMetacritic(libreria.getNombre()));
            libreria.setImagen(em.imagenSteamDB(libreria.getNombre()));
            if(libreria.getImagen().endsWith(".webm")) libreria.setImagen(ei.imagenImdb2(libreria.getNombre()));
        }else{
            libreria.setImdbMetacritic("IMDB : " + ei.puntuacionIMDB(libreria.getNombre()));
            libreria.setImagen(ei.imagenImdb2(libreria.getNombre()));
        }

        return l.create(libreria, miSession);
    }

    public ArrayList<Libreria> readAll(){
        return l.readAll(miSession);
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

    public int ranking(Libreria lib){
        return l.ranking(lib, miSession);
    }

    public String update(Libreria libreria){
        return l.update(libreria, miSession);
    }

    public String delete(Libreria libreria){
        return l.delete(libreria, miSession);
    }
}