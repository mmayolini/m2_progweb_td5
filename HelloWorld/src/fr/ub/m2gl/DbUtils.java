package fr.ub.m2gl;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class DbUtils {

	public static int nextDbId() {
		MongoClient mongoClient = null;
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://toto:tototo1@ds239873.mlab.com:39873/web_td5");
			mongoClient = new MongoClient(uri);
			MongoDatabase db = mongoClient.getDatabase("web_td5");
			MongoCollection<Document> collection = db.getCollection("td5");
			FindIterable<Document> fi = collection.find();
			MongoCursor<Document> cursor = fi.iterator();
			int id_max = 0;
			while (cursor.hasNext()) {
				id_max = Math.max(id_max, cursor.next().getInteger("id"));
			}
			cursor.close();
			return id_max + 1;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return -1;
	}

}
