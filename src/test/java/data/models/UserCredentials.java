package data.models;

import java.util.List;

public class UserCredentials {
	private List<Credential> credentials;

	public List<Credential> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}
}
