/*
 * Sha256.java
 *
 * 25 mag 2023
 */
package it.pagopa.maven.depcheck.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 
 * @author Antonio Tarricone
 */
public class Sha256 {
	/**
	 * 
	 */
	private Sha256() {
	}

	/**
	 * 
	 * @param f
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String calculate(File f) throws NoSuchAlgorithmException, IOException {
		byte[] buf = new byte[8192];
		int n = 0;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f))) {
			while ((n = bis.read(buf)) > 0) {
				digest.update(buf, 0, n);
			}
		}
		byte[] sha256 = digest.digest();
		return Base64.getUrlEncoder().encodeToString(sha256);
	}
}