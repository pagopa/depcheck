/*
 * Dependency.java
 *
 * 20 apr 2023
 */
package it.pagopa.depcheck.bean;

/**
 * 
 * @author Antonio Tarricone
 */
public class Dependency {
	/*
	 * 
	 */
	private String artifactId;

	/*
	 * 
	 */
	private String groupId;

	/*
	 * 
	 */
	private String version;

	/*
	 * 
	 */
	private String fileName;

	/*
	 * 
	 */
	private String sha256;

	/**
	 * 
	 */
	public Dependency() {
	}

	/**
	 * @param artifactId
	 * @param groupId
	 * @param version
	 * @param fileName
	 * @param sha256
	 */
	public Dependency(String artifactId, String groupId, String version, String fileName, String sha256) {
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
		this.fileName = fileName;
		this.sha256 = sha256;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the sha256
	 */
	public String getSha256() {
		return sha256;
	}

	/**
	 * @param sha256 the sha256 to set
	 */
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return new StringBuilder(groupId)
			.append(":")
			.append(artifactId)
			.append(":")
			.append(version)
			.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("Dependency [artifactId=")
			.append(artifactId)
			.append(", groupId=")
			.append(groupId)
			.append(", version=")
			.append(version)
			.append(", fileName=")
			.append(fileName)
			.append(", sha256=")
			.append(sha256)
			.append("]")
			.toString();
	}
}