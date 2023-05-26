/*
 * DependenciesData.java
 *
 * 20 apr 2023
 */
package it.pagopa.maven.depcheck.bean;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Antonio Tarricone
 */
public class DependenciesData {
	/*
	 * 
	 */
	private List<Dependency> dependencies;

	/**
	 * 
	 */
	public DependenciesData() {
	}

	/**
	 * @param dependencies
	 */
	public DependenciesData(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @return the dependencies
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * 
	 */
	public void sort() {
		Collections.sort(dependencies);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("DependenciesData [dependencies=").append(dependencies).append("]").toString();
	}
}