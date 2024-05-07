package CRUD;

import org.hibernate.Session;

import java.util.ArrayList;

public interface LibreriaDAO<T> {
    String create(T entity, Session session);
    String delete(T entity, Session session);
    String update(T entity, Session session);
    ArrayList<T> readAll(Session session);
}