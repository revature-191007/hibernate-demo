package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.daos.PokemonDao;
import com.revature.models.Pokemon;

public class PokemonService {

	Logger logger = Logger.getRootLogger();
	PokemonDao pokemonDao = new PokemonDao();
	
	
	public Pokemon getPokemonById(int id) {
		logger.trace("Retrieving pokemon: " + id);
		Pokemon pokemon = pokemonDao.getPokemonById(id);
		pokemon.getName();
		return pokemon;
	}

	public Pokemon save(Pokemon pokemon) {
		logger.trace("Saving pokemon");
		return pokemonDao.savePokemon(pokemon);
	}

	public Pokemon update(Pokemon pokemon) {
		return pokemonDao.merge(pokemon);
	}

	public List<Pokemon> findAll() {
		return pokemonDao.findAll();
	}

	public Pokemon getPokemonByName(String name) {
		return pokemonDao.getPokemonByName(name);
	}

	public List<Pokemon> getPokemonByType(String type) {
		return pokemonDao.getPokemonByType(type);
	}

	public List<Pokemon> getPokemonByMinHeight(double minHeight) {
		return pokemonDao.getPokemonByMinHeight(minHeight);
	}

	public List<Pokemon> getPokemonByMaxWeight(double maxWeight) {
		return pokemonDao.getPokemonByMaxWeight(maxWeight);
	}

}
