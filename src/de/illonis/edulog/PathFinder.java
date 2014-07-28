package de.illonis.edulog;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simplifies resource locating.
 * 
 * @author illonis
 * 
 */
public class PathFinder {

	private final static Logger L = EduLog.getLoggerFor(PathFinder.class
			.getName());

	/**
	 * Retrieves the path where the programm jar is located.
	 * 
	 * @return the jar's location.
	 */
	public static URL getBaseDir() {
		try {
			// try another way
			CodeSource source = PathFinder.class.getProtectionDomain()
					.getCodeSource();
			if (source != null) {
				URL url2 = new URL(source.getLocation(), "../");
				return url2;
			} else {
				URL url = ClassLoader.getSystemClassLoader().getResource(".");
				if (url != null) {
					URL parent = new URL(url, "../");
					return parent;
				} else {
					throw new RuntimeException(
							"Base directory could not be resolved.");
				}
			}
		} catch (MalformedURLException e) {
			System.out.println("base dir not found.");
			return null;
		}
	}

	/**
	 * Returns an url that points to a file relative to program folder.<br>
	 * The path is built by combining {@link #getBaseDir()} with the fileName.
	 * 
	 * @param fileName
	 *            the file name.
	 * @return a URI pointing to that file.
	 */
	public static URI findFile(String fileName) {
		try {
			URI uri = new URL(PathFinder.getBaseDir(), fileName).toURI();
			return uri;
		} catch (MalformedURLException e) {
			L.log(Level.SEVERE, "Malformed URL!", e);
		} catch (URISyntaxException e) {
			L.log(Level.SEVERE, "URISyntaxException", e);
		}
		return null;
	}
}
