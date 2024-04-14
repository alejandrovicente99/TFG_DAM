package CRUD;

import org.hibernate.Session;

public interface LibreriaDAO<T> {
    String create(T entity, Session session);
    String delete(T entity, Session session);
    String update(T entity, Session session);
    String readAll(Session session);

}