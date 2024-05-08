package CRUD;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public interface LibreriaDAO<T> {
    String create(T entity, Session session);
    String delete(T entity, Session session);
    String update(T entity, Session session);
    ArrayList<T> readAll(Session session);
    List<String> readTipos(Session session);
    ArrayList<T> find(Session session, String cb, String tf);
    ArrayList<T> findByType(Session session, String tipo);
    int ranking(String nombre, Session session);
}