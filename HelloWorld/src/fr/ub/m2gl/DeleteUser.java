package fr.ub.m2gl;

import javax.ws.rs.DELETE;
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

@Path("/user/delete/{id}")
public class DeleteUser extends ResourceConfig {

	public DeleteUser() {
		// Register resources and providers using package-scanning.
		packages("fr.ub.m2gl");

		register(MyObjectMapperProvider.class);

		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");

	} 
	
	@DELETE
	public void deleteUser(@PathParam("id")String str_id) {
		int id = Integer.valueOf(str_id);
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			Bson filter = Filters.eq("id", id);
			collection.deleteOne(filter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}
}