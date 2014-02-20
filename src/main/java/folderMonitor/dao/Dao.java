/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderMonitor.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import folderMonitor.controllers.parameters.PagingInfo;
import folderMonitor.domain.Mappable;

//@Component(value = "folderMonitorDao")
@Repository(value = "folderMonitorDao")
public class Dao extends CrudOperations {

	static Logger logger = LoggerFactory.getLogger(Dao.class);

	// public static void main(String[] args) {
	// ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringConfig.xml");
	// Dao dao = (Dao) context.getBean("folderMonitorDao");
	// Article a = dao.find(Article.class, 2);
	// System.out.println("a.getCode()=" + a.getCode());
	// }

	@Transactional(readOnly = false)
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	public <T> T findByNamedQuery(Class<T> entityClass, String namedQuery, Map<String, Object>... parameters) {

		Query q = entityManager.createNamedQuery(namedQuery);
		for (Map<String, Object> p : parameters) {
			for (Entry<String, Object> e : p.entrySet()) {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
		Object singleResult = q.getSingleResult();
		return (T) singleResult;
	}

	public <T> List<T> findListByNamedQuery(Class<T> entityClass, String namedQuery, PagingInfo info, Map<String, Object>... parameters) {
		Query q = entityManager.createNamedQuery(namedQuery);
		bindParameters(q, parameters);
		if (info != null) {
			setPaging(q, info);
		}
		return q.getResultList();
	}

	private void bindParameters(Query q, Map<String, Object>... multiParams) {
		for (Map<String, Object> p : multiParams) {
			for (Entry<String, Object> e : p.entrySet()) {
				try {
					q.setParameter(e.getKey(), e.getValue());
					logger.debug("binding {} with {}", new Object[] { e.getKey(), e.getValue() });
				} catch (Exception ex) {
				}
			}
		}
	}

	public <T extends Mappable> List<T> findListBySqlQuery(Class<T> entityClass, String sqlQuery, PagingInfo info, Map<String, Object>... multiParams) {
		if (info != null) {
			sqlQuery = setOrder(sqlQuery, info, entityClass);
		}
		Query q = entityManager.createNativeQuery(sqlQuery, entityClass);
		bindParameters(q, multiParams);
		if (info != null) {
			setPaging(q, info);
		}
		return q.getResultList();
	}

	private <T extends Mappable> String setOrder(String sqlQuery, PagingInfo info, Class<T> entityClass) {
		try {
			T newInstance = entityClass.newInstance();
			Set<String> keySet = newInstance.toMap().keySet(); // list of table columns
			if (keySet.contains(info.getSort()) && (info.getDir().equalsIgnoreCase("desc") || info.getDir().equalsIgnoreCase("asc"))) {
				sqlQuery += " order by " + info.getSort() + " " + info.getDir();
			} else {
				if (info.getSort() != null || info.getDir() != null) {
					logger.error("Sort or Dir are unvalid " + info.toMap());
				}
			}
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return sqlQuery;
	}

	private void setPaging(Query q, PagingInfo info) {
		if (info.getStart() != null) {
			q.setFirstResult(info.getStart());
		}
		if (info.getLimit() != null) {
			q.setMaxResults(info.getLimit());
		}
	}
}
