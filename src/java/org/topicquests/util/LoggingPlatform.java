package org.topicquests.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoggingPlatform {
	private static LoggingPlatform instance = null;
	private List<Tracer> tracers;
	private Logger log;

	protected LoggingPlatform(String loggerPropertiesFilePath) {
		PropertyConfigurator.configure(ConfigurationHelper.findPath(loggerPropertiesFilePath));
		this.log = Logger.getLogger(LoggingPlatform.class);
		this.tracers = new ArrayList();
	}

	public static LoggingPlatform getLiveInstance() {
		if (instance == null) {
			throw new RuntimeException("LoggingPlatform not initialized");
		} else {
			return instance;
		}
	}

	public static LoggingPlatform getInstance(String loggerPropertiesFilePath) {
		if (instance == null) {
			instance = new LoggingPlatform(loggerPropertiesFilePath);
		}

		return instance;
	}

	public void logDebug(String msg) {
		this.log.debug(msg);
	}

	public void logError(String msg, Exception e) {
		if (e == null) {
			this.log.error(msg);
		} else {
			this.log.error(msg, e);
		}

	}

	public void record(String msg) {
		this.log.info(msg);
	}

	public Tracer getTracer(String agentName) {
		Tracer t = new Tracer(agentName, this);
		this.tracers.add(t);
		return t;
	}

	public void shutDown() {
		Iterator itr = this.tracers.iterator();

		while (itr.hasNext()) {
			((Tracer) itr.next()).shutDown();
		}

	}
}
