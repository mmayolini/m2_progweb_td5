package fr.ub.m2gl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserResource {

	public static String userToJSON(User user) {
		ObjectMapper map = new ObjectMapper();
		try {
			return map.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static User JSONToUser(String json) {
		ObjectMapper map = new ObjectMapper();
		try {
			return map.readValue(json, User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
