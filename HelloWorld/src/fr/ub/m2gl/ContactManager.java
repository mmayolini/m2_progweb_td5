package fr.ub.m2gl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Path("/contact")
public class ContactManager extends ResourceConfig {

	public ContactManager() {
		// Register resources and providers using package-scanning.
		packages("fr.ub.m2gl");

		register(MyObjectMapperProvider.class);

		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");

	}

	@GET
	@Produces("text/html")
	public String listUsers() {
		String res = "";
		ArrayList<User> listUsers = new ArrayList<User>();
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");

			FindIterable<Document> fi = collection.find();
			MongoCursor<Document> cursor = fi.iterator();
			while (cursor.hasNext()) {
				listUsers.add(UserResource.JSONToUser(cursor.next().toJson()));
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		res += "<form action=\"contact\" method=\"POST\">";
		res += "<p>First name : <input type=\"text\" name=\"firstname\"/></p>";
		res += "<p>Last name : <input type=\"text\" name=\"lastname\"/></p>";
		res += "<p><input type=\"submit\" value=\"submit\"/></p>";
		res += "</form>";
		res += "<ul>";
		for (User u : listUsers) {
			res += "<li>" + u.toString() + "</li>";
		}
		res += "</ul>";
		/*
		 * res += "<script>"; res +=
		 * "if(window.history.replaceState){window.history.replaceState(null,null,window.location.href);}";
		 * res += "</script>";
		 */
		return res;
	}

	@POST
	public Response addUser(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname) {
		int id = DbUtils.nextDbId();
		User user = new User(id, firstname, lastname);
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			String jsonString = UserResource.userToJSON(user);
			Document doc = Document.parse(jsonString);
			collection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		try {
			return Response.temporaryRedirect(new URI("/contact")).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
