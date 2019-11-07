package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

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
