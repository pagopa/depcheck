/*
 * DependenciesRetriever.java
 *
 * 20 apr 2023
 */
package it.pagopa.depcheck.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import it.pagopa.depcheck.bean.Dependency;

/**
 * 
 * @author Antonio Tarricone
 */
public class DependenciesRetriever {
	/*
	 * 
	 */
	private Log log;

	/**
	 * 
	 * @param log
	 */
	public DependenciesRetriever(Log log) {
		this.log = log;
	}

	/**
	 * 
	 * @param project
	 * @return
	 */
	public List<Dependency> retrieve(MavenProject project) {
		log.info("Retrieving of " + project.getName() + " dependencies...");
		List<Dependency> dependencies = Stream.of(
			project.getArtifacts().stream(),
			project.getPluginArtifacts().stream())
			.flatMap(a -> a)
			.map(a -> {
				try {
					String sha256 = "";
					String fileName = "";
					if (a.getFile() == null) {
						log.warn(String.format("SHA-256 of %s:%s:%s cannot be computed.", a.getGroupId(), a.getArtifactId(), a.getVersion()));
					} else {
						sha256 = Sha256.calculate(a.getFile());
						fileName = a.getFile().getName();
					}

					Dependency dependency = new Dependency(a.getArtifactId(), a.getGroupId(), a.getVersion(), fileName, sha256);
					log.info(dependency.toString());
					return dependency;
				} catch (NoSuchAlgorithmException | IOException e) {
					log.error(String.format("Error calculating SHA-256 %s:%s:%s.", a.getGroupId(), a.getArtifactId(), a.getVersion()));
					log.error(e);
					throw new RuntimeException(e);
				}
			})
			.toList();

		if (project.hasParent()) {
			log.info("Retrieving of parent dependencies...");
			List<Dependency> parentDependencies = retrieve(project.clone().getParent());
			dependencies.addAll(parentDependencies);
		}

		return dependencies;
	}
}