/*
 * DependenciesData.java
 *
 * 20 apr 2023
 */
package it.pagopa.depcheck.bean;

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
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
}