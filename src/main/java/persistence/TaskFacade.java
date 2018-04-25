package persistence;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


public class TaskFacade implements business.TaskManager {
	
	//@PersistenceContext(unitName = "TasksMicroService") // Only works in EJB
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
    
    public List<Task> findByTitle(String title) {
    	return em.createNamedQuery("Tasks.findByTitle")
    		    .setParameter("title", title)
    		    .getResultList();
    }
    
    public List<presentation.Task> findByTitleAndUserId(String title, long userId) {
    	return em.createNamedQuery("Tasks.findByTitleAndUserId")
    		    .setParameter("title", title).setParameter("userId", userId)
    		    .getResultList();
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

    public List<Task> findRange(int from, int to) {
    	int[] range = {from, to};
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Task> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }


    
    /*
    public static void main(String[] args) {
    	TaskFacade tf = new TaskFacade();
    	Task t = tf.findByTitle("Proyecto Arqui").get(0);
    	
    	Task new_t = new Task(null, "Diseño Arco", (long) 7778, (long) 3);
    	System.out.println(new_t);
    	//tf.create(new_t);
    	
    	Task update_t = tf.findByTitleAndUserId("Proyecto Arqui", 1).get(0);
    	update_t.setCompletedDate(BigInteger.valueOf(7756));
    	//tf.edit(update_t);
    	
    	//tf.remove(tf.find((long) 7));
    	
    	
    	System.out.println("ID 1: "+tf.find((long) 1));
    	System.out.println("TITLE: "+tf.findByTitle("Proyecto Arqui"));
    	System.out.println("TITLE and UserId: "+tf.findByTitleAndUserId("Proyecto Arqui", 1));
    	System.out.println("Find by user: "+tf.findByUser((long) 1));
    	System.out.println("ALL: "+tf.findAll());
    	System.out.println("Single id: "+t.getId() + " title: "+t.getTitle()+" desc: "+t.getDescription());
    	System.out.println("Count: "+tf.count());
    }
    */
    
}
