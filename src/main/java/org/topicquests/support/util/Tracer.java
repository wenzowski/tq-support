package org.topicquests.support.util;

import org.topicquests.support.util.DateUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Tracer extends Thread {
	private LoggingPlatform log;
	private String fileName;
	private List<Long> timestamps;
	private List<String> messages;
	private boolean isRunning = true;
	private PrintWriter out;
	private TextFileHandler handler;
	private long lineCount = 0L;
	private long flushCount = 100L;
	private long maxLineCount = 100000L;

	public Tracer(String agentName, LoggingPlatform p) {
		this.log = p;
		this.fileName = agentName;
		this.timestamps = new ArrayList();
		this.messages = new ArrayList();
		this.handler = new TextFileHandler();
		this.createFile();
		this.isRunning = true;
		this.lineCount = 0L;
		this.start();
	}

	private void createFile() {
		try {
			if (this.out != null) {
				this.out.flush();
				this.out.close();
			}

			String fname = this.fileName + DateUtil.defaultTimestamp(System.currentTimeMillis()) + ".txt.gz";
			System.out.println(fname);
			fname = fname.replaceAll(" ", "_");
			fname = fname.replaceAll(":", "_");
			System.out.println(fname);
			this.out = this.handler.getGZipWriter(fname);
		} catch (Exception var2) {
			this.log.logError(var2.getMessage(), var2);
			var2.printStackTrace();
		}

	}

	public void shutDown() {
		List var1 = this.messages;
		synchronized (this.messages) {
			this.isRunning = false;
			this.messages.notifyAll();
		}

		try {
			if (this.out != null) {
				this.out.flush();
				this.out.close();
			}
		} catch (Exception var3) {
			this.log.logError(var3.getMessage(), var3);
		}

	}

	public void trace(long timestamp, String message) {
		List var4 = this.messages;
		synchronized (this.messages) {
			this.timestamps.add(new Long(timestamp));
			this.messages.add(message);
			this.messages.notifyAll();
		}
	}

	public void run() {
		Long theTime = null;
		String theMessage = null;

		while (this.isRunning) {
			List var3 = this.messages;
			synchronized (this.messages) {
				if (this.messages.isEmpty()) {
					try {
						this.messages.wait();
					} catch (Exception var6) {
						;
					}
				} else if (this.isRunning && !this.messages.isEmpty()) {
					theMessage = (String) this.messages.remove(0);
					theTime = (Long) this.timestamps.remove(0);
				}
			}

			if (this.isRunning && theMessage != null) {
				this.handleMessage(theMessage, theTime);
				theMessage = null;
				theTime = null;
			}
		}

	}

	void handleMessage(String msg, Long t) {
		long tv = t.longValue();
		String dt = "";
		if (tv > 0L) {
			dt = DateUtil.defaultTimestamp(t.longValue()) + " ";
		}

		this.out.print(dt + msg + "\n");
		++this.lineCount;
		if (this.lineCount % this.flushCount == 0L) {
			try {
				this.out.flush();
			} catch (Exception var7) {
				this.log.logError(var7.getMessage(), var7);
			}
		}

		if (this.lineCount > this.maxLineCount) {
			this.createFile();
		}

	}
}
