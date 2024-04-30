/*
 * Dependency.java
 *
 * 20 apr 2023
 */
package it.pagopa.maven.depcheck.bean;

/**
 * 
 * @author Antonio Tarricone
 */
public class Dependency implements Comparable<Dependency> {
	/*
	 * 
	 */
	private String id;

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
	private String sha256;

	/**
	 * 
	 */
	public Dependency() {
	}

	/**
	 * @param id
	 * @param artifactId
	 * @param groupId
	 * @param version
	 * @param sha256
	 */
	public Dependency(String id, String artifactId, String groupId, String version, String sha256) {
		this.id = id;
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
		this.sha256 = sha256;
	}

	/**
	 * @return the sha256
	 */
	public String getSha256() {
		return sha256;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("Dependency [id=")
			.append(id)
			.append(", artifactId=")
			.append(artifactId)
			.append(", groupId=")
			.append(groupId)
			.append(", version=")
			.append(version)
			.append(", sha256=")
			.append(sha256)
			.append("]")
			.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Dependency o) {
		int c = id.compareTo(o.id);
		if (c == 0) {
			c = groupId.compareTo(o.groupId);
			if (c == 0) {
				c = artifactId.compareTo(o.artifactId);
				if (c == 0) {
					c = version.compareTo(o.version);
					if (c == 0) {
						c = sha256.compareTo(o.sha256);
					}
				}
			}
		}
		return c;
	}
}