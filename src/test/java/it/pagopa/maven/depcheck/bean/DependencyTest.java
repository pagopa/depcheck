/*
 * DependencyTest.java
 *
 * 26 mag 2023
 */
package it.pagopa.maven.depcheck.bean;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Antonio Tarricone
 */
public class DependencyTest {
	@Test
	public void test1() {
		Dependency d1 = new Dependency("0", "2", "1", "3", "4");
		Dependency d2 = new Dependency("0", "2", "1", "3", "5");
		assertTrue(d1.compareTo(d2) < 0);
	}

	@Test
	public void test2() {
		Dependency d1 = new Dependency("0", "2", "1", "3", "4");
		Dependency d2 = new Dependency("0", "2", "1", "6", "5");
		assertTrue(d1.compareTo(d2) < 0);
	}

	@Test
	public void test3() {
		Dependency d1 = new Dependency("0", "2", "1", "3", "4");
		Dependency d2 = new Dependency("0", "7", "1", "6", "5");
		assertTrue(d1.compareTo(d2) < 0);
	}

	@Test
	public void test4() {
		Dependency d1 = new Dependency("0", "2", "1", "3", "4");
		Dependency d2 = new Dependency("0", "7", "8", "6", "5");
		assertTrue(d1.compareTo(d2) < 0);
	}

	@Test
	public void test5() {
		Dependency d1 = new Dependency("0", "2", "1", "3", "4");
		Dependency d2 = new Dependency("9", "7", "8", "6", "5");
		assertTrue(d1.compareTo(d2) < 0);
	}
}