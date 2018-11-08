package fr.ub.m2gl;

import javax.ws.rs.Path;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Path("/user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private int id;
	private String firstname = "default";
	private String lastname = "default";

	public User() {

	}

	public User(int identifiant, String first, String last) {
		id = identifiant;
		firstname = first;
		lastname = last;
	}
	
	public int getID() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String toString() {
		return firstname + " " + lastname;
	}

}
