/*
 * DependenciesDataMojo.java
 *
 * 20 apr 2023
 */
package it.pagopa.depcheck;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import it.pagopa.depcheck.bean.Dependency;

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
	 * @param f
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private String calculateSha256(File f) throws NoSuchAlgorithmException, IOException {
		byte[] buf = new byte[8192];
		int n = 0;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f))) {
			while ((n = bis.read(buf)) > 0) {
				digest.update(buf, 0, n);
			}
		}
		byte[] sha256 = digest.digest();
		return Base64.getUrlEncoder().encodeToString(sha256);
	}
	
	/**
	 * 
	 * @return
	 */
	protected List<Dependency> retrieveDependencies() {
		getLog().info("Retrieving of " + project.getName() + " dependencies...");

		Stream<Artifact> artifacts = null;
		if (includePlugins) {
			getLog().info("Plugins will be included.");
			artifacts = Stream.of(project.getArtifacts().stream(), project.getPluginArtifacts().stream()).flatMap(a -> a);
		} else {
			getLog().info("Plugins will not be included.");
			artifacts = project.getArtifacts().stream();
		}

		List<Dependency> dependencies = artifacts.map(a -> {
			try {
				String sha256 = "";
				String fileName = "";
				if (a.getFile() == null) {
					getLog().warn(String.format("SHA-256 of %s:%s:%s cannot be computed.", a.getGroupId(), a.getArtifactId(), a.getVersion()));
				} else {
					sha256 = calculateSha256(a.getFile());
					fileName = a.getFile().getName();
				}

				Dependency dependency = new Dependency(a.getArtifactId(), a.getGroupId(), a.getVersion(), fileName, sha256);
				getLog().info(dependency.toString());
				return dependency;
			} catch (NoSuchAlgorithmException | IOException e) {
				getLog().error(String.format("Error calculating SHA-256 %s:%s:%s.", a.getGroupId(), a.getArtifactId(), a.getVersion()));
				getLog().error(e);
				throw new RuntimeException(e);
			}
		}).toList();

		if (includeParent && project.hasParent()) {
			getLog().info("Retrieving of parent dependencies...");
			List<Dependency> parentDependencies = retrieveDependencies();
			dependencies.addAll(parentDependencies);
		}

		return dependencies;
	}
}