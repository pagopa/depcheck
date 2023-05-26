/*
 * DependenciesDataGeneratorMojo.java
 *
 * 20 apr 2023
 */
package it.pagopa.maven.depcheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.google.gson.GsonBuilder;

import it.pagopa.maven.depcheck.bean.DependenciesData;
import it.pagopa.maven.depcheck.bean.Dependency;

/**
 * 
 * @author Antonio Tarricone
 */
@Mojo(name = "generate", configurator = "", defaultPhase = LifecyclePhase.NONE, aggregator = true, instantiationStrategy = InstantiationStrategy.PER_LOOKUP, requiresDependencyCollection = ResolutionScope.TEST, requiresDependencyResolution = ResolutionScope.TEST, requiresOnline = false, requiresProject = true, threadSafe = false)
public class DependenciesDataGeneratorMojo extends DependenciesDataMojo {
	/**
	 * The main method.
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		/*
		 * Get dependencies.
		 */
		List<Dependency> dependencies = retrieveDependencies();

		/*
		 * Serialize in a JSON the list of dependencies.
		 */
		String json = new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.create()
			.toJson(new DependenciesData(dependencies));

		/*
		 * Write the JSON in a file.
		 */
		File depSha256 = new File(project.getBasedir(), fileName);
		try {
			depSha256.getParentFile().mkdirs();
			depSha256.createNewFile();
			try (PrintWriter out = new PrintWriter(depSha256)) {
				out.print(json);
			}
		} catch (IOException e) {
			getLog().error(e);
			throw new MojoExecutionException(e);
		}
	}
}