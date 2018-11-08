package fr.ub.m2gl;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Toto {

	public static void main(String[] args) {
		int id = Integer.valueOf("5");
		User user = new User(id, "litou", "latou");
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			Bson filter = Filters.eq("id", id);
			collection.updateOne(filter, new Document("$set", new Document("firstname","litou")));
			collection.updateOne(filter, new Document("$set", new Document("lastname","latou")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

	}
}
