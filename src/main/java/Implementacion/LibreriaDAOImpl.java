package Implementacion;

import CRUD.LibreriaDAO;
import ORM.Libreria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LibreriaDAOImpl implements LibreriaDAO {

    @Override
    public String create(Object entity, Session session) {
        Transaction txn = null;
        String linea = "";
        try{
            txn = session.beginTransaction();
            session.persist(entity);
            txn.commit();
            linea = "Registro de empleado ingresado en BBDD";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea=  "Error al añadir el empleado a la BBDD";
        }
        return linea;
    }

    @Override
    public String delete(Object entity, Session session) {
        Transaction txn = null;
        String linea = "";
        try{
            txn = session.beginTransaction();
            session.remove(entity);
            txn.commit();
            linea = "Empleado borrado de la BBDD";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea = "Error al eliminar el empleado de la BBDD";
        }
        return linea;
    }

    @Override
    public String update(Object entity, Session session) {
        Transaction txn = null;
        String linea = "";
        try{
            txn = session.beginTransaction();
            session.merge(entity);
            txn.commit();
            linea = "Empleado actualizado";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea = "Error al actualizar el empleado de la BBDD";
        }
        return linea;
    }

    @Override
    public ArrayList<Libreria> readAll(Session session) {
        try{

            List<Libreria> libreria = session.createQuery("FROM Libreria", Libreria.class).getResultList();

            ArrayList<Libreria> librerias = new ArrayList<>(libreria);

            return librerias;
        } catch (Exception ignored) {

        }
        return null;
    }
}
