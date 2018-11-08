package fr.ub.m2gl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Path("/user/modify/{id}/{firstname}/{lastname}")
public class ModifyUser extends ResourceConfig {

	public ModifyUser() {
		// Register resources and providers using package-scanning.
		packages("fr.ub.m2gl");

		register(MyObjectMapperProvider.class);

		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");

	}
	
	@POST
	public void modifyUser(@PathParam("id")String str_id, @PathParam("firstname")String firstname, @PathParam("lastname")String lastname) {
		int id = Integer.valueOf(str_id);
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			Bson filter = Filters.eq("id", id);
			collection.updateOne(filter, new Document("$set", new Document("firstname",firstname)));
			collection.updateOne(filter, new Document("$set", new Document("lastname",lastname)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}
	
}