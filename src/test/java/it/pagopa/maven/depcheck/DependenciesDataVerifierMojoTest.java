/*
 * DependenciesDataVerifierMojoTest.java
 *
 * 21 apr 2023
 */
package it.pagopa.maven.depcheck;

import java.io.File;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;

/**
 * 
 * @author Antonio Tarricone
 */
public class DependenciesDataVerifierMojoTest extends AbstractMojoTestCase {
	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWoPluginsHashOk() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-wo-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWPluginsHashOk() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MyArtifactStub artifact3 = new MyArtifactStub();
		artifact3.setId("art_3");
		artifact3.setArtifactId("artifact_3");
		artifact3.setFile(getTestFile("src/test/resources/unit-test/artifact_3.txt"));
		artifact3.setGroupId("group_3");
		artifact3.setVersion("version_3");

		MyArtifactStub artifact4 = new MyArtifactStub();
		artifact4.setId("art_4");
		artifact4.setArtifactId("artifact_4");
		artifact4.setFile(getTestFile("src/test/resources/unit-test/artifact_4.txt"));
		artifact4.setGroupId("group_4");
		artifact4.setVersion("version_4");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_W_PLUGINS");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(Set.of(artifact3, artifact4));
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-w-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", true);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWParentWPluginsHashOk() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MyArtifactStub artifact3 = new MyArtifactStub();
		artifact3.setId("art_3");
		artifact3.setArtifactId("artifact_3");
		artifact3.setFile(getTestFile("src/test/resources/unit-test/artifact_3.txt"));
		artifact3.setGroupId("group_3");
		artifact3.setVersion("version_3");

		MyArtifactStub artifact4 = new MyArtifactStub();
		artifact4.setId("art_4");
		artifact4.setArtifactId("artifact_4");
		artifact4.setFile(getTestFile("src/test/resources/unit-test/artifact_4.txt"));
		artifact4.setGroupId("group_4");
		artifact4.setVersion("version_4");

		MyArtifactStub artifact5 = new MyArtifactStub();
		artifact5.setId("art_5");
		artifact5.setArtifactId("artifact_5");
		artifact5.setFile(getTestFile("src/test/resources/unit-test/artifact_5.txt"));
		artifact5.setGroupId("group_5");
		artifact5.setVersion("version_5");

		MyArtifactStub artifact6 = new MyArtifactStub();
		artifact6.setId("art_6");
		artifact6.setArtifactId("artifact_6");
		artifact6.setFile(getTestFile("src/test/resources/unit-test/artifact_6.txt"));
		artifact6.setGroupId("group_6");
		artifact6.setVersion("version_6");

		MavenProject parent = new MavenProject();
		parent.setName("PARENT_OF_PROJECT_STUB_W_PARENT_W_PLUGINS");
		parent.setArtifacts(Set.of(artifact5));
		parent.setPluginArtifacts(Set.of(artifact6));
		parent.setParent(null);

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_W_PARENT_W_PLUGINS");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(Set.of(artifact3, artifact4));
		project.setParent(parent);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/w-parent-w-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", true);
		setVariableValueToObject(mojo, "includeParent", true);
		mojo.execute();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWoPluginsMissingDependency() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_MISSING_DEPENDENCY");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-wo-plugins-missing-dependency.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);

		try {
			mojo.execute();
			fail();
		} catch (MojoExecutionException e) {
		} catch (Throwable e) {
			fail();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWoPluginsWrongHash() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_MISSING_DEPENDENCY");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-wo-plugins-wrong-hash.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		try {
			mojo.execute();
			fail();
		} catch (MojoExecutionException e) {
		} catch (Throwable e) {
			fail();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	// public void testWoParentWoPluginsUnusedHash() throws Exception {
	// MyArtifactStub artifact1 = new MyArtifactStub(); artifact1.setId("art_1");
	// artifact1.setArtifactId("artifact_1");
	// artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
	// artifact1.setGroupId("group_1");
	// artifact1.setVersion("version_1");
	//
	// MyArtifactStub artifact2 = new MyArtifactStub(); artifact2.setId("art_2");
	// artifact2.setArtifactId("artifact_2");
	// artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
	// artifact2.setGroupId("group_2");
	// artifact2.setVersion("version_2");
	//
	// MavenProject project = new MavenProject();
	// project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_UNUSED_HASH");
	// project.setArtifacts(Set.of(artifact1, artifact2));
	// project.setPluginArtifacts(null);
	// project.setParent(null);
	//
	// File pom = getTestFile("src/test/resources/unit-test/pom.xml");
	// DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
	// setVariableValueToObject(mojo, "project", project);
	// setVariableValueToObject(mojo, "fileName",
	// "src/test/resources/unit-test/wo-parent-w-plugins-verify-ok.json");
	// setVariableValueToObject(mojo, "includePlugins", false);
	// setVariableValueToObject(mojo, "includeParent", false);
	// try {
	// mojo.execute();
	// fail();
	// } catch (MojoExecutionException e) {
	// } catch (Throwable e) {
	// fail();
	// }
	// }

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWoPluginsFileNotFound() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(getTestFile("src/test/resources/unit-test/artifact_2.txt"));
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_UNUSED_HASH");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/not_found.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		try {
			mojo.execute();
			fail();
		} catch (MojoFailureException e) {
		} catch (Throwable e) {
			fail();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWPluginsWoDependencies() throws Exception {
		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_W_PLUGINS_WO_DEPENDENCIES");
		project.setArtifacts(null);
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-w-plugins-wo-dependencies.json");
		setVariableValueToObject(mojo, "includePlugins", true);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void testWoParentWoPluginsUnreachableDependency() throws Exception {
		MyArtifactStub artifact1 = new MyArtifactStub();
		artifact1.setId("art_1");
		artifact1.setArtifactId("artifact_1");
		artifact1.setFile(getTestFile("src/test/resources/unit-test/artifact_1.txt"));
		artifact1.setGroupId("group_1");
		artifact1.setVersion("version_1");

		MyArtifactStub artifact2 = new MyArtifactStub();
		artifact2.setId("art_2");
		artifact2.setArtifactId("artifact_2");
		artifact2.setFile(null);
		artifact2.setGroupId("group_2");
		artifact2.setVersion("version_2");

		MavenProject project = new MavenProject();
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_UNREACHABLE_DEPENDENCY");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataVerifierMojo mojo = (DependenciesDataVerifierMojo) lookupMojo("verify", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "src/test/resources/unit-test/wo-parent-wo-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();
	}
}