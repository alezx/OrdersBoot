package folderMonitor.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

public class CrudOperations {

	@PersistenceContext
	protected EntityManager entityManager;

	// protected org.springframework.orm.jpa.DefaultJpaDialect jpaDialect;

	// @PersistenceContext(unitName = "entityManagerFactoryOrderDB")
	// public void setEntityManager(EntityManager entityManager) {
	// this.entityManager = entityManager;
	// }

	// public void setJpaDialect(org.springframework.orm.jpa.DefaultJpaDialect jpaDialect) {
	// this.jpaDialect = jpaDialect;
	// }

	// @Transactional(value = "txManager", readOnly = false)
	public void remove(Object o) {
		entityManager.remove(o);
	}

	@Transactional(readOnly = false)
	public void remove(Object o, Object id) {
		entityManager.remove(find(o.getClass(), id));
	}

	// @Transactional(value = "txManager", readOnly = true)
	public <T extends Object> T find(Class<T> type, Object id) {
		return entityManager.find(type, id);
	}

	@Transactional(readOnly = false)
	public int executeUpdate(String q) {
		Query insertQuery = entityManager.createNativeQuery(q);
		return insertQuery.executeUpdate();
	}
}
