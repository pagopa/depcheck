/*
 * DependenciesDataGeneratorMojo.java
 *
 * 20 apr 2023
 */
package it.pagopa.depcheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.google.gson.GsonBuilder;

import it.pagopa.depcheck.bean.DependenciesData;
import it.pagopa.depcheck.bean.Dependency;
import it.pagopa.depcheck.util.DependenciesRetriever;

/**
 * 
 * @author Antonio Tarricone
 */
@Mojo(name = "generate", configurator = "", defaultPhase = LifecyclePhase.NONE, aggregator = true, instantiationStrategy = InstantiationStrategy.PER_LOOKUP, requiresDependencyCollection = ResolutionScope.TEST, requiresDependencyResolution = ResolutionScope.TEST, requiresOnline = false, requiresProject = true, threadSafe = false)
public class DependenciesDataGeneratorMojo extends AbstractMojo {
	/*
	 * The Maven project.
	 */
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	/**
	 * The main method.
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		/*
		 * Get dependencies.
		 */
		DependenciesRetriever retriever = new DependenciesRetriever(getLog());
		List<Dependency> dependencies = retriever.retrieve(project);

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
		File depSha256 = new File(project.getBasedir(), "dep-sha256.json");
		try {
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