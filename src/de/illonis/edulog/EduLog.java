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
	private final static int DEFAULT_FILE_SIZE = 32384;
	private static Level logLimit = Level.WARNING;
	private static long startTime = 0;

	private static FileHandler fileTxt;
	private static ConsoleHandler consoleHandler;
	private static LogFormatter formatterTxt;
	private static String currentLogFile;

	/**
	 * Initializes EduLog with given logfile located in the default location. By
	 * default, logging threshold is set to ALL.
	 * 
	 * @param logFileName
	 *            the name of the logfile.
	 * @param maxSize
	 *            the maximum size of the log file.
	 * @throws IOException
	 *             if creation of logfile failed.
	 */
	public static void init(String logFileName, int maxSize) throws IOException {
		init("", logFileName, maxSize);
	}

	/**
	 * Initializes EduLog with given logfile. By default, logging threshold is
	 * set to ALL.
	 * 
	 * @param directoryPath
	 *            the path to the directory to create the logfile at. must be
	 *            absolute
	 * @param logFileName
	 *            the name of the logfile.
	 * @param maxSize
	 *            the maximum size of the log file.
	 * @throws IOException
	 *             if creation of logfile failed.
	 */
	public static void init(String directoryPath, String logFileName,
			int maxSize) throws IOException {
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

		if (directoryPath != null && !directoryPath.isEmpty()) {
			SubdirFileHandler.createDir(directoryPath);
			fileTxt = new SubdirFileHandler(directoryPath + "/" + logFileName,
					maxSize, 3, true);
		} else {
			SubdirFileHandler.createDir();
			fileTxt = new SubdirFileHandler(SubdirFileHandler.LOG_DIR + "/"
					+ logFileName, maxSize, 3, true);
		}
		// create txt Formatter
		formatterTxt = new LogFormatter();
		fileTxt.setFormatter(formatterTxt);
		fileTxt.setLevel(Level.ALL);
		logger.addHandler(fileTxt);
		currentLogFile = logFileName;
		logger.info("Logging started. Logfile: " + logFileName);
		setBasicLogLimit(Level.ALL);
	}

	/**
	 * Returns the name of the logfile used.
	 * 
	 * @return log file name.
	 */
	public static String getCurrentLogFile() {
		return currentLogFile;
	}

	/**
	 * Initializes EduLog using the given logfile name and the default file
	 * size. By default, logging threshold is set to ALL.
	 * 
	 * @param logFileName
	 *            the name of the logfile.
	 * @throws IOException
	 *             if creation of logfile failed.
	 * @see #init(String,int)
	 */
	public static void init(String logFileName) throws IOException {
		init(logFileName, DEFAULT_FILE_SIZE);
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
	 * @see #init(String,int)
	 */
	public static void init() throws IOException {
		init(DEFAULT_LOG_FILE, DEFAULT_FILE_SIZE);
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
