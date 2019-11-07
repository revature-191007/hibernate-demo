package com.revature.daos;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

	
	
}
