package Implementacion;

import CRUD.LibreriaDAO;
import ORM.Libreria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
    public String readAll(Session session) {
        /*try{
            String lista = "";

            try {
                List<Libreria> libreria = session.createQuery("FROM Libreria", Libreria.class).getResultList();

                if (libreria.isEmpty()) {
                    lista = "No hay empleados registrados en la base de datos.";
                } else {
                    lista = lista + "Lista de todos los empleados:\n";
                    for (Libreria libreria1 : libreria) {
                        Query query1 = session.createQuery("SELECT nomDepto FROM Departamento  WHERE idDepto = :idDepartamento");
                        query1.setParameter("idDepartamento", empleado.getDepartamentos().getIdDepto());
                        List<Integer> resultado1 = query1.list();

                        EmpleadosDatosProf empleado1 = session.get(EmpleadosDatosProf.class, empleado.getDni());

                        lista += "-DNI: " + empleado.getDni() + " -Nombre: " + empleado.getNombre() + " -Departamento: " +  resultado1.get(0) + " -Salario: " + empleado1.getSalario() + "\n";
                    }
                }
            } catch (Exception e) {
                return "Error al leer";
            }
            return lista;
        } catch (Exception e) {
            return "Error al leer";
        }*/
        return null;
    }
}
