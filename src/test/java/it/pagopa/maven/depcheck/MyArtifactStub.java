/*
 * MyArtifactStub.java
 *
 * 30 mag 2023
 */
package it.pagopa.maven.depcheck;

import org.apache.maven.plugin.testing.stubs.ArtifactStub;

/**
 * 
 * @author Antonio Tarricone
 */
public class MyArtifactStub extends ArtifactStub {
	/*
	 * 
	 */
	private String id;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
}