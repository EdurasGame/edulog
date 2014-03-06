package de.illonis.edulog;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides logging features for Eduras?.<br>
 * For logging, {@link java.util.logging.Logger} is used. This class handles
 * initialization and structuration. By default, this logger supports logging to
 * console and file.<br>
 * The logging system has to be initialized once at startup by calling
 * {@link #init()}. You may pass a filename if you do not want to use the
 * default one ({@value #DEFAULT_LOG_FILE}).<br>
 * Logging thresholds can be adjusted using {@link #setBasicLogLimit(Level)},
 * {@link #setConsoleLogLimit(Level)} and {@link #setFileLogLimit(Level)}. Set
 * one of the limits to {@link Level#OFF} to disable that logging.
 * 
 * @author illonis
 * 
 */
public final class EduLog {

	public final static int VERSION = 2;

	private final static String DEFAULT_LOG_FILE = "logfile.txt";
	private static Level logLimit = Level.WARNING;
	private static long startTime = 0;

	private static FileHandler fileTxt;
	private static ConsoleHandler consoleHandler;
	private static LogFormatter formatterTxt;

	/**
	 * Initializes EduLog with given logfile. By default, logging threshold is
	 * set to WARNING.
	 * 
	 * @param logFileName
	 *            the name of the logfile.
	 * @throws IOException
	 *             if creation of logfile failed.
	 */
	public static void init(String logFileName) throws IOException {
		startTime = System.currentTimeMillis();
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		Handler[] handlers = logger.getHandlers();
		for (Handler handler : handlers) {
			logger.removeHandler(handler);
		}
		consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		consoleHandler.setLevel(Level.ALL);
		logger.addHandler(consoleHandler);
		fileTxt = new FileHandler(logFileName, 8096, 1, true);
		// create txt Formatter
		formatterTxt = new LogFormatter();
		fileTxt.setFormatter(formatterTxt);
		fileTxt.setLevel(Level.ALL);
		logger.addHandler(fileTxt);
		logger.info("Logging started. Logfile: " + logFileName);
		setBasicLogLimit(Level.ALL);
	}

	static long getStartTime() {
		return startTime;
	}

	/**
	 * Initializes EduLog using default logfile. By default, logging threshold
	 * is set to WARNING.
	 * 
	 * @throws IOException
	 *             if creation of logfile failed.
	 * @see #init(String)
	 */
	public static void init() throws IOException {
		init(DEFAULT_LOG_FILE);
	}

	/**
	 * Limits logging to prevent less severe errors from being displayed. This
	 * disables log reporting for all messages with lower severeness than given
	 * level.<br>
	 * For example, if you pass {@link Level#WARNING}, messages with
	 * {@link Level#INFO} and {@link Level#FINE} will not be logged.<br>
	 * <b>Note:</b> Setting this to a higher level than console / file logging
	 * prevents lower messages to be logged.
	 * 
	 * @param limit
	 *            level limit. Messages below will not be logged.
	 */
	public static void setBasicLogLimit(Level limit) {
		logLimit = limit;
		Logger.getLogger("").setLevel(limit);
	}

	/**
	 * Limits logging to file to given logging level.
	 * 
	 * @param limit
	 *            new logging threshold.
	 */
	public static void setFileLogLimit(Level limit) {
		fileTxt.setLevel(limit);
	}

	/**
	 * Limits logging to console to given logging level.
	 * 
	 * @param limit
	 *            new logging threshold.
	 */
	public static void setConsoleLogLimit(Level limit) {
		consoleHandler.setLevel(limit);
	}

	/**
	 * Retrieves the logger for given class.
	 * 
	 * @param className
	 *            name of logging class.
	 * @return the logger.
	 */
	public static Logger getLoggerFor(String className) {
		Logger logger = Logger.getLogger(className);
		logger.setLevel(logLimit);
		return logger;
	}
}
