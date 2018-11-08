package fr.ub.m2gl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Path("/users")
public class ListUsers extends ResourceConfig {

	public ListUsers() {
		// Register resources and providers using package-scanning.
		packages("fr.ub.m2gl");

		register(MyObjectMapperProvider.class);

		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");

	}

	@GET
	@Produces("application/json")
	public String usersJSON() {
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			FindIterable<Document> fi = collection.find();
			MongoCursor<Document> cursor = fi.iterator();
			String res = "{\"users\":[";
			while (cursor.hasNext()) {
				res += cursor.next().toJson() + ",";
			}
			cursor.close();
			res = res.substring(0, res.length() - 1);
			res += "]}";
			return res;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return "error";
	}

	
}