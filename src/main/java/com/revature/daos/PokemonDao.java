package com.revature.daos;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.revature.models.Location;
import com.revature.models.Pokemon;
import com.revature.util.HibernateUtil;


/**
 * Core Hibernate Session CRUD operations
 * Create Operations:
 * 1. save - Returns Serializable id
 * 2. persist - returns void - JPA specification
 * Read operations:
 * 3. get - Returns object with specified id or null if it doesnt exist
 * 4. load - Returns object or proxy of object with specified ID. Proxy
 * 		will attempt initialization when a non-id field is accessed or
 * 		Hibernate.initialize is called with the proxy as a parameter.
 * 		Proxies will throw ObjectNotFoundException & 
 * 		LazyInitializationException under some circumstances.
 * Update operations:
 * 5. Update - Updates a detached instance returning persistent instance.
 * 			If object is already persistent within the session throws
 * 			NonUniqueObjectException. Requires Transaction.
 * 6. Merge - JPA. Applies the state of provided object to a persistent
 * 			instance if one exists, otherwise creates a persistent 
 * 			instance, returning the persistent instance. Can also be
 * 			used to create new instances.
 * Delete operations:
 * 7. Delete - It's just that simple.
 *
 *
 * Proxy Related Exceptions:
 * 1. LazyInitializationException - An uninitialized proxy attempted
 * to initialize after the session was closed.
 * 2. ObjectNotFoundException - A proxy attempted to initialize an object
 * with an ID that was not represented on the database.
 * 
 * Other Exceptions:
 * 1. NonUniqueObjectException - Thrown when using update when the object
 * to be updated is already in the session cache.
 * 
 */
public class PokemonDao {

	static Logger log = Logger.getRootLogger();
	
	public List<Location> getPokemonLocations(int pokeId) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			Pokemon pokemon = session.get(Pokemon.class, pokeId);
			if (pokemon == null) {
				return null;
			}
			List<Location> locations = pokemon.getAppearingLocations();
			Hibernate.initialize(locations);
			return locations;
		}
	}
	
	public Pokemon getPokemonById(int id) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			/**
			 * (Aside)
			 * Idempotency - the effect of 1 request = of n requests where n > 1
			 * GET - Idempotent - safe
			 * POST - Not idempotent - unsafe
			 * PUT - Idempotent - unsafe
			 * DELETE - Idempotent - unsafe
			 * 
			 */
			
			/*
			 * Thanks to Hibernate's session cache this pokemon will only be
			 * retrieved from the database once
			 */
//			session.get(Pokemon.class, id);
//			session.get(Pokemon.class, id);
//			session.get(Pokemon.class, id);
//			session.get(Pokemon.class, id);
//			session.get(Pokemon.class, id);
//			session.get(Pokemon.class, id);
			
			
			Pokemon pokemon = session.get(Pokemon.class, id);
//			Hibernate.initialize(pokemon);
			return pokemon;
		}
	}

	/**
	 * Persistent states of hibernate and JPA
	 *
	 * TRANSIENT - Object has no database representation
	 * PERSISTENT - Has database representation and is currently being tracked
	 * 		by an ongoing session. (cached)
	 * DETACHED - Has database representation but is not currently being tracked.
	 * 		(is not cached).
	 */
	
	public Pokemon savePokemon(Pokemon pokemon) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			session.save(pokemon);
			return pokemon;
		}
	}

	public Pokemon update(Pokemon pokemon) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Pokemon otherInstance = session.get(Pokemon.class, 2);
			session.update(pokemon);
			// Hibernate has a feature called 'automatic dirty checking'
			// When we commit, changes that have been made on 
			// persistant objects will be updated on the database
			// representation
			pokemon.setHeight(20);
			transaction.commit();
			return pokemon;
		}
	}
	
	public Pokemon merge(Pokemon pokemon) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Pokemon otherInstance = session.get(Pokemon.class, 2);
			Pokemon tracked = (Pokemon) session.merge(pokemon);
			// Hibernate has a feature called 'automatic dirty checking'
			// When we commit, changes that have been made on 
			// persistant objects will be updated on the database
			// representation
			System.out.println(tracked == otherInstance);
			pokemon.setHeight(20);
			transaction.commit();
			return tracked;
		}
	}

	/**
	 * HQL - A method of querying the database using a SQL like
	 * string, but using entity names and fields rather than 
	 * table names and column names.  Additionally, the syntax
	 * can be somewhat abbreviated as you don't need to worry about
	 * columns - it assumes you want complete entities.
	 * @return
	 */
	public List<Pokemon> findAll() {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
//			String hql = "select p from Pokemon p";
			String hql = "from Pokemon";
			List<Pokemon> pokemon = session
					.createQuery(hql, Pokemon.class)
					.getResultList();
			return pokemon;
		}
	}

	public Pokemon getPokemonByName(String name) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			String hql = "from Pokemon p where upper(p.name) like upper(:name)";
			Pokemon pokemon = session.createQuery(hql, Pokemon.class)
					.setParameter("name", name, StringType.INSTANCE)
					.getSingleResult();
			return pokemon;
		}
	}

	/*
	 * Criteria is a fully object oriented fashion of querying
	 * the database.  Normal SQL queries are strings and therefore
	 * logical and syntax errors aren't always easy to identify.
	 * 
	 */
	public List<Pokemon> getPokemonByType(String type) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Pokemon> pokeQuery = cb.createQuery(Pokemon.class);
			Root<Pokemon> root = pokeQuery.from(Pokemon.class);
			
			pokeQuery.select(root)
				.where(cb.equal(root.get("primaryType").get("name"), type));
			
			Query query = session.createQuery(pokeQuery);
			List<Pokemon> pokemon = query.getResultList();
			log.info(pokemon.size());
			return pokemon;
		}
	}

	/*
	 * Native Query - Native query is a query in raw SQL syntax rather than HQL.
	 * Native queries are useful when Hibernate does not support a mapping to the SQL
	 * syntax or operation you intend to conduct. You should avoid using native queries
	 * if at all possible, due to Hibernate not necessarily being able to track the 
	 * results correctly, which risks invalidating all data being stored in all
	 * hibernate caches.
	 * 
	 * The Configuration connects to the SessionFactory.
	 * The SessionFactory connects to the Session.
	 * The Query connects to the Session.
	 * The Transaction connects to the Session.
	 * The Criteria connects to the Session.
	 */
	public List<Pokemon> getPokemonByMinHeight(double minHeight) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			String sql = "SELECT * FROM pokemon WHERE height >= :minHeight";
			List<Pokemon> pokemon = session.createNativeQuery(sql)
					.setParameter("minHeight", minHeight)
					.addEntity(Pokemon.class)
					.getResultList();
			return pokemon;	
		}
	}

	public List<Pokemon> getPokemonByMaxWeight(double maxWeight) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			return session.getNamedQuery("getPokemonByMaxWeight")
				.setParameter("weight", maxWeight)
				.getResultList();
		}
	}
	
	
}




