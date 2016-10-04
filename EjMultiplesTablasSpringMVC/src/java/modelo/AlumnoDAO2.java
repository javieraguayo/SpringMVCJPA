/*
 persistencia lectura de datos unida sin necesidad de usar inner join
 */
package modelo;

import entidades.Alumno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *Patrones de diseño
 * DTO se encarga la informacion de la base de datos atributos de la clase Gety Set
   DAO Insercion borrado consulta CRUD 
 */
@Service //Spring clases con esta anotacion de spring framewwork
public class AlumnoDAO2 {

    @PersistenceContext //spring pide marcalo con este sufijo
    private EntityManager em;//javax.persisten los metodos vienen creados CRUD

    @Transactional(rollbackFor = {ServicioException.class})//para trabajar con clase de excepcioens creada "servicio exceptio
    //@Transactional
    //metodo registrar
    //no retorno limitaciones de metodos de EntityManager
    public void create(Alumno dto) throws ServicioException {//llamamos a la entidades(Alumno) 2 espacio
        em.persist(dto);//llamo al EntityManager   //manejamos la excepcion de forma externa "ServicioException"
//        em.merge(dto); //actualizar 
//        em.remove(dto); //borrar 
//        em.find(Alumno.class, "111");//busca si existe un Alumno con ese rut clase Alumno retorna T(alumno)
//       //buscar por pk 

    }

    //consulta simple Entity
    public Alumno readByRut(String rut) throws SecurityException {
        return em.find(Alumno.class, rut);
    }

    //Otra forma de consulta JPQL (Consulta Simple)
    public Alumno readByRutJPQL(String rut) throws SecurityException {
        String sql = "Select a from Alumno a Where a.rut = :rut";//lenguaje creado para la api de persistencia
        //JPQL se creo hace una query a la clase a la entity 
        //se hace un duplicado de la BD en clases trabajo con objetos y los objetos internamente se conectan con la BD
        Query q = em.createQuery(sql);
        q.setParameter("rut", rut); // rut sea igual a un comodin seteo el parametro ":rut"

        try {
            return (Alumno) q.getSingleResult();//retorno un solo resultado 
        } catch (Exception e) {//no se puede retornar una excepcion que no existe
            return null;
        }

    }
    //otra forma de query JPQL (Consulta simple)
    public List<Alumno> readAllJPQL() throws SecurityException {
        String sql = "Select a from Alumno a";//traigo todos los alumnos

        Query q = em.createQuery(sql);
        return q.getResultList(); //sin nada retorna lista de largo 0
    }
    
    public boolean readByRutJPQLCreate(String rut) throws SecurityException{
    String sql = "Select a from Alumno a Where a.rut = :rut";
    
    Query q = em.createQuery(sql);
    q.setParameter("rut", rut);
    
    if (q.getResultList().size()>0) {
            return true;
        }
        else{
            return false;
        }
    }
    //query JPQL (insertar)
    public int createJPQL(Alumno dto) throws SecurityException{
        if (readByRutJPQLCreate(dto.getRut())) {
            return 0;
        }
        else{
            em.persist(dto);
            return 1;
        }
    }
}
