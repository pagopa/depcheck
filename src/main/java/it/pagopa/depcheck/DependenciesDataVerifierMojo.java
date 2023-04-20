/*
 * DependenciesDataVerifierMojo.java
 *
 * 19 apr 2023
 */
package it.pagopa.depcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import it.pagopa.depcheck.bean.DependenciesData;
import it.pagopa.depcheck.bean.Dependency;

/**
 * 
 * @author Antonio Tarricone
 */
@Mojo(name = "verify", configurator = "", defaultPhase = LifecyclePhase.VALIDATE, aggregator = true, instantiationStrategy = InstantiationStrategy.PER_LOOKUP, requiresDependencyCollection = ResolutionScope.TEST, requiresDependencyResolution = ResolutionScope.TEST, requiresOnline = false, requiresProject = true, threadSafe = false)
public class DependenciesDataVerifierMojo extends DependenciesDataMojo {
	/**
	 * The main method.
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		/*
		 * Get dependencies.
		 */
		List<Dependency> dependencies = retrieveDependencies();

		/*
		 * Load dependencies data.
		 */
		getLog().info("Loading dependencies data...");
		try (JsonReader reader = new JsonReader(new FileReader(new File(project.getBasedir(), fileName)))) {
			DependenciesData dependenciesData = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader, DependenciesData.class);

			/*
			 * Transform list of dependencies to map.
			 */
			Map<String, String> map = dependenciesData.getDependencies().stream()
				.collect(Collectors.toMap(
					Dependency::getId,
					Dependency::getSha256));

			/*
			 * Verify SHA-256.
			 */
			getLog().info("Verifing SHA-256 of dependencies...");
			boolean[] result = new boolean[] {
				true
			};
			dependencies.forEach(d -> {
				String id = d.getId();
				String expected = map.get(id);
				String found = d.getSha256();
				if (expected == null || expected.isEmpty()) {
					getLog().warn(String.format("SHA-256 of %s cannot be verified.", id));
				} else {
					if (!expected.equals(found)) {
						getLog().warn(String.format("SHA-256 of %s doesn't match: expected %s, found %s", id, expected, found));
						result[0] = false;
					}
				}
			});
			if (!result[0]) {
				getLog().error("SHA-256 verification failed.");
				throw new MojoExecutionException("SHA-256 verification failed.");
			}
		} catch (FileNotFoundException e) {
			getLog().error("dep-sha256.json not found.");
			throw new MojoFailureException("dep-sha256.json not found.");
		} catch (IOException e) {
			getLog().error(e);
			throw new MojoFailureException(e);
		}
	}
}