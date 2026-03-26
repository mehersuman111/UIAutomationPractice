package core.dataprovider;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.UserCredentials;
import models.Credential;

public class CredentialsProviderManager {
	public static final String CREDENTIAL_FILE = ".\\src\\test\\resources\\testData\\credentials\\CredentialList.json";
	
	public static List<Credential> getAllCredentials(){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(CREDENTIAL_FILE);
			models.UserCredentials userCredentials = objectMapper.readValue(file, models.UserCredentials.class);
			return userCredentials.getCredentials();
		}catch (Exception e) {
			throw new RuntimeException("Failed to read credentials: " + e.getMessage());
		}
	}
	
	public static Credential getCredentialsByRole(String userRole){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(CREDENTIAL_FILE);
			models.UserCredentials userCredentials = objectMapper.readValue(file, models.UserCredentials.class);
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
