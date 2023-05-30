/*
 * DependenciesDataMojo.java
 *
 * 20 apr 2023
 */
package it.pagopa.maven.depcheck;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import it.pagopa.maven.depcheck.bean.Dependency;
import it.pagopa.maven.depcheck.util.Sha256;

/**
 * 
 * @author Antonio Tarricone
 */
public abstract class DependenciesDataMojo extends AbstractMojo {
	/*
	 * The Maven project.
	 */
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	protected MavenProject project;

	/*
	 * The name of the file containing dependencies data.
	 */
	@Parameter(property = "fileName", required = false, defaultValue = "dep-sha256.json")
	protected String fileName;

	/*
	 * If true, the plugins are included as dependencies.
	 */
	@Parameter(property = "includePlugins", required = false, defaultValue = "false")
	protected boolean includePlugins;

	/*
	 * If true, the parent dependencies are included.
	 */
	@Parameter(property = "includeParent", required = false, defaultValue = "false")
	protected boolean includeParent;

	/**
	 * 
	 * @return
	 */
	protected List<Dependency> retrieveDependencies() {
		return retrieveDependencies(project);
	}

	/**
	 * 
	 * @param specProject
	 * @return
	 */
	protected List<Dependency> retrieveDependencies(MavenProject specProject) {
		getLog().info("Retrieving of " + specProject.getName() + " dependencies...");

		Stream<Artifact> artifacts = null;

		Set<Artifact> artifactSet = specProject.getArtifacts();

		if (includePlugins) {
			getLog().info("Plugins will be included.");
			Set<Artifact> pluginArtifactSet = specProject.getPluginArtifacts();
			if (pluginArtifactSet == null) {
				pluginArtifactSet = Set.of();
			}
			artifacts = Stream.of(artifactSet.stream(), pluginArtifactSet.stream()).flatMap(a -> a);
		} else {
			getLog().info("Plugins will not be included.");
			artifacts = artifactSet.stream();
		}

		List<Dependency> dependencies = artifacts.map(a -> {
			try {
				String sha256 = "";
				if (a.getFile() == null) {
					getLog().warn(String.format("SHA-256 of %s cannot be computed.", a.getId()));
				} else {
					sha256 = Sha256.calculate(a.getFile());
				}

				Dependency dependency = new Dependency(a.getId(), a.getArtifactId(), a.getGroupId(), a.getVersion(), sha256);
				getLog().info(dependency.toString());
				return dependency;
			} catch (NoSuchAlgorithmException | IOException e) {
				getLog().error(String.format("Error calculating SHA-256 %s.", a.getId()));
				getLog().error(e);
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());

		if (includeParent && specProject.hasParent()) {
			getLog().info("Retrieving of parent dependencies...");
			List<Dependency> parentDependencies = retrieveDependencies(specProject.getParent());
			dependencies.addAll(parentDependencies);
		}

		return dependencies;
	}
}