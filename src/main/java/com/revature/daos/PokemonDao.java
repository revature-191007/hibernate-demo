package com.revature.daos;

import org.hibernate.Session;

import com.revature.models.Pokemon;
import com.revature.util.HibernateUtil;

public class PokemonDao {

	public Pokemon getPokemonById(int id) {
		try(Session session = HibernateUtil.sessionFactory.openSession()) {
			/**
			 * Idempotency - the effect of 1 request = of n requests where n > 1
			 * GET - Idempotent - safe
			 * POST - Not idempotent - unsafe
			 * PUT - Idempotent - unsafe
			 * DELETE - Idempotent - unsafe
			 * 
			 */
			
			session.get(Pokemon.class, id);
			session.get(Pokemon.class, id);
			session.get(Pokemon.class, id);
			session.get(Pokemon.class, id);
			session.get(Pokemon.class, id);
			session.get(Pokemon.class, id);
			return session.get(Pokemon.class, id);
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
			session.persist(pokemon);
			return pokemon;
		}
	}

	
	
}
