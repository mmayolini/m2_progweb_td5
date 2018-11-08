package fr.ub.m2gl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("/user/add/{firstname}/{lastname}")
public class AddUser extends ResourceConfig {

	public AddUser() {
		// Register resources and providers using package-scanning.
		packages("fr.ub.m2gl");

		register(MyObjectMapperProvider.class);

		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");

	}
	
	@POST
	public void addUser(@PathParam("firstname")String firstname, @PathParam("lastname")String lastname) {
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
	}
	
}