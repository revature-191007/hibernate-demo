package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.revature.models.Pokemon;
import com.revature.services.PokemonService;
import com.revature.util.HibernateUtil;

public class PokemonServlet extends HttpServlet {
	private PokemonService pokemonService = new PokemonService();
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		super.service(req, resp);
	}
	
	@Override
	public void init() throws ServletException {
		HibernateUtil.configureHibernate();
		om.registerModule(new Hibernate5Module());
	}
	
	@Override
	public void destroy() {
		HibernateUtil.sessionFactory.close();
	}
	
	/**
	 * /pokemon/{id} - retrieve pokemon with id of {id}
	 * /pokemon - Retrieve all pokemon
	 *  For simplicity lets assume these query parameters can't be combined
	 * /pokemon?type=fire
	 * /pokemon?minHeight=x
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String info = req.getPathInfo();
		
		String minHeight = req.getParameter("minHeight");
		if (minHeight != null) {
			handleGetPokemonByMinHeight(req, resp, minHeight);
			return;
		}
		
		String maxWeight = req.getParameter("maxWeight");
		if (maxWeight != null) {
			handleGetPokemonByMaxWeight(req, resp, maxWeight);
			return;
		}
		
		String type = req.getParameter("type");
		if (type != null) {
			handleGetPokemonByType(req, resp, type);
			return;
		}
		
		if (info == null) {
			handleGetAllPokemon(req, resp);
			return;
		}
		
		String[] parts = info.split("/");
		
		if (parts.length <= 1) {
			handleGetAllPokemon(req, resp);
			return;
		}
		int id = 0;
		
		try {
			id = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			String name = parts[1];
			getPokemonByName(req, resp, name);
			return;
		}
		
		Pokemon pokemon = pokemonService.getPokemonById(id);
		
		om.writeValue(resp.getWriter(), pokemon);
	}
	
	private void handleGetPokemonByMinHeight(HttpServletRequest req, HttpServletResponse resp, String minHeightString) throws IOException {
		try {
			double minHeight = Integer.parseInt(minHeightString);
			List<Pokemon> pokemon = pokemonService.getPokemonByMinHeight(minHeight);
			om.writeValue(resp.getWriter(), pokemon);
		} catch(NumberFormatException e) {
			resp.setStatus(400);
		}
	}
	
	private void handleGetPokemonByMaxWeight(HttpServletRequest req, HttpServletResponse resp, String minHeightString) throws IOException {
		try {
			double maxWeight = Integer.parseInt(minHeightString);
			List<Pokemon> pokemon = pokemonService.getPokemonByMaxWeight(maxWeight);
			om.writeValue(resp.getWriter(), pokemon);
		} catch(NumberFormatException e) {
			resp.setStatus(400);
		}
	}

	private void handleGetPokemonByType(HttpServletRequest req, HttpServletResponse resp, String type) throws IOException {
		List<Pokemon> pokemon = pokemonService.getPokemonByType(type);
		om.writeValue(resp.getWriter(), pokemon);
	}

	private void getPokemonByName(HttpServletRequest req, HttpServletResponse resp, String name) throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Pokemon pokemon = pokemonService.getPokemonByName(name);
			om.writeValue(resp.getWriter(), pokemon);
		} catch (NoResultException e) {
			// Handles Hibernate NoResult from getSingleResult
			resp.setStatus(404);
		}
	}

	private void handleGetAllPokemon(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		List<Pokemon> pokemon = pokemonService.findAll();
		om.writeValue(resp.getWriter(), pokemon);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Pokemon pokemon = om.readValue(req.getReader(), Pokemon.class);
		Pokemon savedPokemon = pokemonService.save(pokemon);
		om.writeValue(resp.getWriter(), savedPokemon);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Pokemon pokemon = om.readValue(req.getReader(), Pokemon.class);
		Pokemon savedPokemon = pokemonService.update(pokemon);
		om.writeValue(resp.getWriter(), savedPokemon);
	}
	
	
}
