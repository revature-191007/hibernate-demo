package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.revature.models.Pokemon;
import com.revature.models.Type;
import com.revature.services.TypeService;

public class TypeServlet extends HttpServlet{

	TypeService typeService = new TypeService();
	ObjectMapper om = new ObjectMapper();
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		super.service(req, res);
	}
	
	@Override
	public void init() throws ServletException {
		om.registerModule(new Hibernate5Module());
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		Type type = typeService.getTypeById(id);
		
		om.writeValue(resp.getWriter(), type);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Type type = om.readValue(req.getReader(), Type.class);
		Type savedType = typeService.save(type);
		om.writeValue(resp.getWriter(), savedType);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Type type = om.readValue(req.getReader(), Type.class);
		Type savedType = typeService.update(type);
		om.writeValue(resp.getWriter(), savedType);
	}
	
}
