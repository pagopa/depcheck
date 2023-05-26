# depcheck
Maven plugin to generate and verify sha256 of project dependencies.

## Goals
### depcheck:generate
Generates the file with the sha256 of project dependencies, that will be used to verify them during building phases.

### depcheck:verify
Verifies the sha256 of project dependencies using the file generated with the goal depcheck:generate.

## Usage
Declare in your pom.xml the depcheck plugin:

```xml
<plugin>
	<groupId>it.pagopa.maven</groupId>
	<artifactId>depcheck</artifactId>
	<version>1.0.0</version>
	<executions>
		<execution>
			<phase>validate</phase>
			<goals>
				<goal>verify</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<fileName>urlenctest-dep-sha256.json</fileName>
		<includePlugins>false</includePlugins>
		<includeParent>false</includeParent>
	</configuration>
</plugin>
```

In this way during the validation phase of the project, the sha256 of the dependencies will be verified.

To generate the file with sha256:

```bash
mvn depcheck:generate
```

### Parameters
| Name           | Type    | Description                                                                                           |
| -------------- | ------- | ----------------------------------------------------------------------------------------------------- |
| fileName       | String  | File with sha256 of project dependencies. It must be relative to project home (where is the pom.xml). |
| includePlugins | boolean | If true, the plugins will be taken into account.                                                      |
| includeParent  | boolean | If true, the dependencies of the parent project will be taken into account.                           |