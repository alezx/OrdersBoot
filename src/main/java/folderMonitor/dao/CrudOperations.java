package folderMonitor.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;


public class CrudOperations {

	@PersistenceContext
    protected EntityManager entityManager;
	
    //protected org.springframework.orm.jpa.DefaultJpaDialect jpaDialect;
    
	//@PersistenceContext(unitName = "entityManagerFactoryOrderDB")
    //public void setEntityManager(EntityManager entityManager) {
    //    this.entityManager = entityManager;
    //}
    
	//public void setJpaDialect(org.springframework.orm.jpa.DefaultJpaDialect jpaDialect) {
	//	this.jpaDialect = jpaDialect;
	//}
	
	

    @Transactional(value = "txManager", readOnly = false)
    public void save(Object o) {
        entityManager.persist(o);
    }

    @Transactional(value = "txManager", readOnly = false)
    public void merge(Object o)  {
        entityManager.merge(o);
    }

    @Transactional(value = "txManager", readOnly = false)
    public void remove(Object o) {
        entityManager.remove(o);
    }
    
    @Transactional(value = "txManager", readOnly = false)
    public void remove(Object o, Object id) {
        entityManager.remove(find(o.getClass(), id));
    }

    @Transactional(value = "txManager", readOnly = true)
    public <T extends Object> T find(Class<T> type, Object id) {
        return entityManager.find(type, id);
    }

    @Transactional(value = "txManager", readOnly = true)
    public <T extends Object> T getSingleResult(String s, Class<T> type) {
        TypedQuery<T> q = entityManager.createQuery(s, type);
        return q.getSingleResult();
    }

    @Transactional(value = "txManager", readOnly = true)
    public <T extends Object> List<T> getResultList(String s, Class<T> type) {
        TypedQuery<T> q = entityManager.createQuery(s, type);
        return q.getResultList();
    }

    @Transactional(readOnly = false)
    public int executeUpdate(String q) {
        Query insertQuery = entityManager.createNativeQuery(q);
        return insertQuery.executeUpdate();
    }
}
