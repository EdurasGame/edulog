package de.illonis.edulog;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;

public class SubdirFileHandler extends FileHandler {

	public final static String LOG_DIR = "logs";

	public static void createDir() {
		URI logDir = PathFinder.findFileRelative(LOG_DIR);
		Path p = Paths.get(logDir);
		try {
			System.out.println("creating dir at " + p);
			Path logPath = Files.createDirectory(p);
			System.out.println("dir created at " + logPath);
		} catch (FileAlreadyExistsException e) {
			System.out.println("dir exists. no problem");
		} catch (IOException e) {
			System.out.println("Could not create logfile directory.");
			e.printStackTrace();
		}
	}

	public SubdirFileHandler() throws IOException, SecurityException {
		super();
	}

	public SubdirFileHandler(String pattern) throws SecurityException,
			IOException {
		super(pattern);
	}

	public SubdirFileHandler(String pattern, boolean append)
			throws SecurityException, IOException {
		super(pattern, append);
	}

	public SubdirFileHandler(String pattern, int limit, int count)
			throws SecurityException, IOException {
		super(pattern, limit, count);
	}

	public SubdirFileHandler(String pattern, int limit, int count,
			boolean append) throws SecurityException, IOException {
		super(pattern, limit, count, append);
	}

	public static void createDir(String directoryPath) {
		URI logDir = PathFinder.findFileAbsolute(directoryPath);
		Path p = Paths.get(logDir);
		try {
			System.out.println("creating dir at " + p);
			Path logPath = Files.createDirectory(p);
			System.out.println("dir created at " + logPath);
		} catch (FileAlreadyExistsException e) {
			System.out.println("dir exists. no problem");
		} catch (IOException e) {
			System.out.println("Could not create logfile directory.");
			e.printStackTrace();
		}
	}

}
