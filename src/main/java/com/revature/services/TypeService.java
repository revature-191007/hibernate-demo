package com.revature.services;

import com.revature.daos.TypeDao;
import com.revature.models.Type;

public class TypeService {

	TypeDao typeDao = new TypeDao();
	
	public Type getTypeById(int id) {
		return typeDao.get(id);
	}

	public Type save(Type type) {
		return typeDao.save(type);
	}

	public Type update(Type type) {
		return typeDao.update(type);
	}

}
