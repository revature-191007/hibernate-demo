package com.revature.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.models.Pokemon;

public class HibernateUtil {

	static Logger log = Logger.getRootLogger();
	public static SessionFactory sessionFactory;
	
	public static void configureHibernate() {
		log.info("Configuring Hibernate");
		
		Configuration configuration = new Configuration()
				.configure()
				.addAnnotatedClass(Pokemon.class)
				// Add username/password from environment variables
				.setProperty("hibernate.connection.username", System.getenv("EM_ROLE"))
				.setProperty("hibernate.connection.password", System.getenv("EM_PASS"));
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
	}
}
