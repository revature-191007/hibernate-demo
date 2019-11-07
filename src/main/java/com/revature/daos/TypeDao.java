package com.revature.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Type;
import com.revature.util.HibernateUtil;

public class TypeDao {

	public Type get(int id) {
		try(Session sess = HibernateUtil.sessionFactory.openSession()){ 
			return sess.get(Type.class, id);
		}
	}

	public Type save(Type type) {
		try(Session sess =  HibernateUtil.sessionFactory.openSession()){ 
			sess.save(type);
			return type;
		}
	}

	public Type update(Type type) {
		try(Session sess =  HibernateUtil.sessionFactory.openSession()){ 
			Transaction tx = sess.beginTransaction();
			sess.update(type);
			tx.commit();
			return type;
		}
	}

}
