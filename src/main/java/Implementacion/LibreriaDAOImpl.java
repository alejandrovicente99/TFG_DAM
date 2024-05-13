package Implementacion;

import CRUD.LibreriaDAO;
import ORM.Libreria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            linea = "Registro ingresado en BBDD";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea=  "Error al añadir el registro a la BBDD";
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
            linea = "Registro borrado de la BBDD";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea = "Error al eliminar el registro de la BBDD";
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
            linea = "Registro actualizado";
        }catch (HibernateException e){
            if(txn != null){
                txn.rollback();
            }
            linea = "Error al actualizar el registro de la BBDD";
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

    @Override
    public List<String> readTipos(Session session) {
        try{
            List<String> tipos = session.createQuery("Select distinct tipo from Libreria order by tipo asc").getResultList();

            return tipos;
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public ArrayList<Libreria> find(Session session, String cb, String tf) {
        String tipo = "";
        try{
            switch(cb){
                case "Nombre": tipo = "nombre";break;
                case "Tipo": tipo = "tipo";break;
                case "Fecha fin": tipo = "fechaFin";break;
                case "Puntuacion": tipo = "puntuacion";break;
            }
            Query query = session.createQuery("FROM Libreria  WHERE " + tipo + " = :tf");
            query.setParameter("tf", tf);
            List<Libreria> libreria = query.list();
            ArrayList<Libreria> librerias = new ArrayList<>(libreria);

            return librerias;
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public ArrayList<Libreria> findByType(Session session, String tipo) {
        try{
            Query query = session.createQuery("FROM Libreria  WHERE tipo = :tipo");
            query.setParameter("tipo", tipo);
            List<Libreria> libreria = query.list();
            ArrayList<Libreria> librerias = new ArrayList<>(libreria);

            return librerias;
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public int ranking(Object entity, Session session) {
        Libreria lib = (Libreria) entity;
        try{
            Query query = session.createQuery("FROM Libreria where tipo =:tipo order by puntuacion desc");
            query.setParameter("tipo", lib.getTipo());
            List<Libreria> libreria = query.list();
            ArrayList<Libreria> librerias = new ArrayList<>(libreria);
            return librerias.indexOf(lib) + 1;
        } catch (Exception ignored) {

        }
        return 0;
    }
}
