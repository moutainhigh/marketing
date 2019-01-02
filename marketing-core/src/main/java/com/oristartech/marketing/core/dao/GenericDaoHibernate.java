package com.oristartech.marketing.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;



public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK>{

	private static final int BATCH_MAX_SIZE = 100;
	
	@Resource(name="sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    //获取Session
    protected Session getSession() {
        return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    }

    private Class<T> persistentClass;

	public GenericDaoHibernate() {
		// 获取当前new的对象的泛型的父类类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型 
		this.persistentClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * Constructor that takes in a class and sessionFactory for easy creation of
	 * DAO.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 * @param sessionFactory
	 *            the pre-configured Hibernate SessionFactory
	 */
	public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
		this.persistentClass = persistentClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(this.persistentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(PK id) {
		T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
		return entity != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T saveOrUpdate(T object) {
		return (T) getHibernateTemplate().merge(object);
	}
	
	@SuppressWarnings("unchecked")
	public PK save(T object) {
		return (PK) getHibernateTemplate().save(object);
	}
	
	@SuppressWarnings("unchecked")
	public void update(T object) {
		getHibernateTemplate().update(object);
	}

	public <T> int batchSaveOrUpdate(final List<T> objects) {
		getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException {
				for (int i = 0; i < objects.size(); i++) {
					session.merge(objects.get(i));
					if (i % BATCH_MAX_SIZE == 0) {
						session.flush();
						session.clear();
					}
				}
				session.flush();
				session.clear();
				return null;
			}
		});
		return objects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		getHibernateTemplate().delete(this.get(id));
	}

	public void remove(T object) {
		getHibernateTemplate().delete(object);
	}

	public void deleteById(final PK id) {
		final String hql = "delete from " + this.persistentClass.getName() + " t where t.id = ? ";
		getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter(0, id);
				query.executeUpdate();
				return null;
			}
		});
	};

	public void deleteByIds(final List<PK> ids) {
		final String hql = "delete from " + this.persistentClass.getName() + " t where t.id in( :ids )";
		getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				query.executeUpdate();
				return null;
			}
		});

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
		String[] params = new String[queryParams.size()];
		Object[] values = new Object[queryParams.size()];

		int index = 0;
		for (String s : queryParams.keySet()) {
			params[index] = s;
			values[index++] = queryParams.get(s);
		}

		return (List<T>) getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
	}
}
