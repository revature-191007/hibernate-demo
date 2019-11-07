package com.revature.demo;

import org.apache.log4j.Logger;

public class LoggingDemo {
	static Logger log = Logger.getRootLogger();
	
	public static void main(String[] args) {
		log.trace("This is a trace log");
		log.debug("This is a debug log");
		log.info("This is an info log");
		log.warn("This is a warning log");
		log.error("This is an error log");
		log.fatal("This is a fatal log");
	}
}
