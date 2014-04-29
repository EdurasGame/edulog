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

	static {
		URI logDir = PathFinder.findFile(LOG_DIR);
		Path p = Paths.get(logDir);
		try {
			System.out.println("creating dir...");
			Files.createDirectory(p);
		} catch (FileAlreadyExistsException e) {

		} catch (IOException e) {
			System.err.println("Could not create logfile directory.");
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

}
