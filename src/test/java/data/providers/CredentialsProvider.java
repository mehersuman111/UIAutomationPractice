package data.providers;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.models.Credential;
import data.models.UserCredentials;

public class CredentialsProvider {
	public static final String CREDENTIAL_FILE = ".\\src\\test\\resources\\testData\\credentials\\CredentialList.json";
	
	public static List<Credential> getAllCredentials(){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(CREDENTIAL_FILE);
			UserCredentials userCredentials = objectMapper.readValue(file, UserCredentials.class);
			return userCredentials.getCredentials();
		}catch (Exception e) {
			throw new RuntimeException("Failed to read credentials: " + e.getMessage());
		}
	}
	
	public static Credential getCredentialsByRole(String userRole){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(CREDENTIAL_FILE);
			UserCredentials userCredentials = objectMapper.readValue(file, UserCredentials.class);
			for(Credential credential:userCredentials.getCredentials()) {
				if(credential.getRole().equals(userRole)) {
					return credential;
				}
			}
		}catch (Exception e) {
			throw new RuntimeException("Failed to read credentials: " + e.getMessage());
		}
	    throw new RuntimeException("Role not found: " + userRole);
	}
}
