/*
 * Sha256Gen.java
 *
 * 25 mag 2023
 */
package it.pagopa.maven.depcheck.util;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author antonio.tarricone
 *
 */
public class Sha256Gen {
	/**
	 * @param args
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		File f = new File("src/test/resources/unit-test/artifact_6.txt");
		System.out.println(Sha256.calculate(f));
	}
}