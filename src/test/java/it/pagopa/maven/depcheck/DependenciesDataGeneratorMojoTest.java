/*
 * DependenciesDataGeneratorMojoTest.java
 *
 * 26 mag 2023
 */
package it.pagopa.maven.depcheck;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import it.pagopa.maven.depcheck.bean.DependenciesData;

/**
 * 
 * @author Antonio Tarricone
 */
public class DependenciesDataGeneratorMojoTest extends AbstractMojoTestCase {
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
		DependenciesDataGeneratorMojo mojo = (DependenciesDataGeneratorMojo) lookupMojo("generate", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "target/test/resources/unit-test/wo-parent-wo-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();

		try (
			JsonReader reader1 = new JsonReader(new FileReader(getTestFile("target/test/resources/unit-test/wo-parent-wo-plugins-verify-ok.json")));
			JsonReader reader2 = new JsonReader(new FileReader(getTestFile("src/test/resources/unit-test/wo-parent-wo-plugins-verify-ok.json")));) {
			DependenciesData dependenciesData1 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader1, DependenciesData.class);
			dependenciesData1.sort();

			DependenciesData dependenciesData2 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader2, DependenciesData.class);
			dependenciesData2.sort();

			assertEquals(dependenciesData1.toString(), dependenciesData2.toString());
		}
	}

	public void testWoParentWoPluginsWExcludedDependenciesHashOk() throws Exception {
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
		project.setName("PROJECT_STUB_WO_PARENT_WO_PLUGINS_W_EXCLUDES");
		project.setArtifacts(Set.of(artifact1, artifact2));
		project.setPluginArtifacts(null);
		project.setParent(null);

		File pom = getTestFile("src/test/resources/unit-test/pom.xml");
		DependenciesDataGeneratorMojo mojo = (DependenciesDataGeneratorMojo) lookupMojo("generate", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "target/test/resources/unit-test/wo-parent-wo-plugins-w-excludes-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", false);
		setVariableValueToObject(mojo, "includeParent", false);
		setVariableValueToObject(mojo, "excludes", Map.of("group_1", List.of("artifact_1")));

		mojo.execute();

		try (
				JsonReader reader1 = new JsonReader(new FileReader(getTestFile("target/test/resources/unit-test/wo-parent-wo-plugins-w-excludes-verify-ok.json")));
				JsonReader reader2 = new JsonReader(new FileReader(getTestFile("src/test/resources/unit-test/wo-parent-wo-plugins-w-excludes-verify-ok.json")));) {
			DependenciesData dependenciesData1 = new GsonBuilder()
					.disableHtmlEscaping()
					.create()
					.fromJson(reader1, DependenciesData.class);
			dependenciesData1.sort();

			DependenciesData dependenciesData2 = new GsonBuilder()
					.disableHtmlEscaping()
					.create()
					.fromJson(reader2, DependenciesData.class);
			dependenciesData2.sort();

			assertEquals(dependenciesData1.toString(), dependenciesData2.toString());
		}
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
		DependenciesDataGeneratorMojo mojo = (DependenciesDataGeneratorMojo) lookupMojo("generate", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "target/test/resources/unit-test/wo-parent-w-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", true);
		setVariableValueToObject(mojo, "includeParent", false);
		mojo.execute();

		try (
			JsonReader reader1 = new JsonReader(new FileReader(getTestFile("target/test/resources/unit-test/wo-parent-w-plugins-verify-ok.json")));
			JsonReader reader2 = new JsonReader(new FileReader(getTestFile("src/test/resources/unit-test/wo-parent-w-plugins-verify-ok.json")));) {
			DependenciesData dependenciesData1 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader1, DependenciesData.class);
			dependenciesData1.sort();

			DependenciesData dependenciesData2 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader2, DependenciesData.class);
			dependenciesData2.sort();

			assertEquals(dependenciesData1.toString(), dependenciesData2.toString());
		}
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
		DependenciesDataGeneratorMojo mojo = (DependenciesDataGeneratorMojo) lookupMojo("generate", pom);
		setVariableValueToObject(mojo, "project", project);
		setVariableValueToObject(mojo, "fileName", "target/test/resources/unit-test/w-parent-w-plugins-verify-ok.json");
		setVariableValueToObject(mojo, "includePlugins", true);
		setVariableValueToObject(mojo, "includeParent", true);
		mojo.execute();

		try (
			JsonReader reader1 = new JsonReader(new FileReader(getTestFile("target/test/resources/unit-test/w-parent-w-plugins-verify-ok.json")));
			JsonReader reader2 = new JsonReader(new FileReader(getTestFile("src/test/resources/unit-test/w-parent-w-plugins-verify-ok.json")));) {
			DependenciesData dependenciesData1 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader1, DependenciesData.class);
			dependenciesData1.sort();

			DependenciesData dependenciesData2 = new GsonBuilder()
				.disableHtmlEscaping()
				.create()
				.fromJson(reader2, DependenciesData.class);
			dependenciesData2.sort();

			assertEquals(dependenciesData1.toString(), dependenciesData2.toString());
		}
	}
}