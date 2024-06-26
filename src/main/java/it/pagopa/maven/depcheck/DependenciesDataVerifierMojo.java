/*
 * DependenciesDataVerifierMojo.java
 *
 * 19 apr 2023
 */
package it.pagopa.maven.depcheck;

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

import it.pagopa.maven.depcheck.bean.DependenciesData;
import it.pagopa.maven.depcheck.bean.Dependency;

/**
 * 
 * @author Antonio Tarricone
 */
@Mojo(name = "verify", configurator = "", defaultPhase = LifecyclePhase.VALIDATE, aggregator = true, instantiationStrategy = InstantiationStrategy.PER_LOOKUP, requiresDependencyCollection = ResolutionScope.TEST, requiresDependencyResolution = ResolutionScope.TEST, requiresOnline = false, requiresProject = true, threadSafe = false)
public class DependenciesDataVerifierMojo extends DependenciesDataMojo {
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws MojoFailureException
	 */
	private Map<String, String> loadDependeciesData(String fileName) throws MojoFailureException {
		File f = new File(project.getBasedir(), fileName);
		try (JsonReader reader = new JsonReader(new FileReader(f))) {
			DependenciesData dependenciesData = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader, DependenciesData.class);

			/*
			 * Transform list of dependencies to map.
			 */
			return dependenciesData.getDependencies().stream()
				.collect(Collectors.toMap(
					Dependency::getId,
					Dependency::getSha256));
		} catch (FileNotFoundException e) {
			getLog().error(f.getAbsolutePath() + " not found.");
			throw new MojoFailureException(f.getAbsolutePath() + " not found.");
		} catch (IOException e) {
			getLog().error(e);
			throw new MojoFailureException(e);
		}
	}

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
		Map<String, String> map = loadDependeciesData(fileName);
		if (addFileName != null) {
			map.putAll(loadDependeciesData(addFileName));
		}

		/*
		 * Verify SHA-256.
		 */
		getLog().info("Verifing SHA-256 of dependencies...");
		boolean[] result = new boolean[] {
			true
		};
		dependencies.forEach(d -> {
			String id = d.getId();
			String expected = map.remove(id);
			String found = d.getSha256();
			if (expected == null) {
				getLog().warn(String.format("SHA-256 of %s is missing. Expected %s", id, found));
				result[0] = false;
			} else {
				if (found.isEmpty()) {
					getLog().warn(String.format("SHA-256 of %s has not been computed and cannot be verified.", id));
				} else {
					if (expected.equals(found)) {
						getLog().info(String.format("SHA-256 of %s matches: expected %s, found %s", id, expected, found));
					} else {
						getLog().warn(String.format("SHA-256 of %s doesn't match: expected %s, found %s", id, expected, found));
						result[0] = false;
					}
				}
			}
		});
		if (!result[0]) {
			getLog().warn("SHA-256 verification failed.");
			throw new MojoExecutionException("SHA-256 verification failed.");
		} else if (!map.isEmpty()) {
			getLog().warn("The following dependencies are not used:");
			map.keySet().stream().forEach(k -> getLog().warn(k));
			// throw new MojoExecutionException("Dependencies not used.");
		}
	}
}