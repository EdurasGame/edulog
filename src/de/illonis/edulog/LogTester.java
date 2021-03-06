package de.illonis.edulog;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogTester {
	private final static Logger LOGGER = EduLog.getLoggerFor(LogTester.class
			.getName());

	public static void main(String[] args) {
		try {
			EduLog.init(LogTester.class.getClassLoader().getResource(".")
					.getPath(), "testLog", 5555555);
			EduLog.setConsoleLogLimit(Level.SEVERE);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		LogTester t = new LogTester();
		t.test();
	}

	public LogTester() {

	}

	void test() {
		LOGGER.severe("abc");
		LOGGER.setLevel(Level.WARNING);

		LOGGER.severe("Info Log");

		LOGGER.warning("Info Log");
		Exception e = new NumberFormatException("my message ex");
		LOGGER.log(Level.SEVERE, "an ex", e);

		LOGGER.info("Info Log");
	}
}
