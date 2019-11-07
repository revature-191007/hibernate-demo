package com.revature.services;

import com.revature.daos.LocationDao;
import com.revature.models.Location;

public class LocationService {

	LocationDao locationDao = new LocationDao();
	
	public Location save(Location location) {
		return locationDao.save(location);
	}

	public Location getTypeById(int id) {
		return locationDao.getById(id);
	}

	public Location update(Location location) {
		return locationDao.update(location);
	}

}
