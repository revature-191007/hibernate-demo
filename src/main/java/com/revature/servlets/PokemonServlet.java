package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Pokemon;
import com.revature.services.PokemonService;
import com.revature.util.HibernateUtil;

public class PokemonServlet extends HttpServlet {
	private PokemonService pokemonService = new PokemonService();
	
	@Override
	public void init() throws ServletException {
		HibernateUtil.configureHibernate();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// /pokemon/{id}
		String info = req.getPathInfo();
		
		if (info == null) {
			resp.setStatus(400);
			return;
		}
		
		String[] parts = info.split("/");
		
		if (parts.length <= 0) {
			resp.setStatus(400);
			return;
		}
		int id = 0;
		
		try {
			id = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			resp.setStatus(400);
			return;
		}
		
		Pokemon pokemon = pokemonService.getPokemonById(id);
		
		ObjectMapper om = new ObjectMapper();
		om.writeValue(resp.getWriter(), pokemon);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Pokemon pokemon = om.readValue(req.getReader(), Pokemon.class);
		Pokemon savedPokemon = pokemonService.save(pokemon);
		om.writeValue(resp.getWriter(), savedPokemon);
		
	}
}
