package persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class TaskFacade implements business.TaskManager {
	
    private EntityManager em;

    private Class<Task> entityClass;

    public TaskFacade() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TasksDB");
    	this.em = emf.createEntityManager();
        this.entityClass = Task.class;
    }

    public void addTask(presentation.Task entity) {
    	em.getTransaction().begin();
    	em.persist(entity);
    	em.getTransaction().commit();
    }

    public void updateTask(presentation.Task entity) {
    	em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void remove(presentation.Task entity) {
    	em.getTransaction().begin();
    	em.remove(em.merge(entity));
        em.getTransaction().commit();
    }
    
    public void removeUser(String userId){
    	List<presentation.Task> userTasks =  this.getUserTasks(userId);
    	for (presentation.Task task: userTasks){
    		this.remove(task);
    	}
    }

    public presentation.Task getTask(long id) {
        return em.find(entityClass, id);
    }
    
    
    public List<presentation.Task> getUserTasks(String userId) {
    	return em.createNamedQuery("Tasks.findByUserId")
    		    .setParameter("userId", userId)
    		    .getResultList();
    }

    public List<presentation.Task> getAllTasks() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
}
