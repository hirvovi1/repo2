package fi.my.pkg;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class Storage {

	private MongoCollection<Document> booksCollection;
	private MongoClient mongoClient;
	private final String dbName;

	public Storage() {
		connect("mongodb://localhost:27017/?retryWrites=false");
		this.dbName = "bookdb";
	}
	
	public Storage(String connectionString, String database) {
		this.dbName = database;
		connect(connectionString);
	}

	public void connect(String connectionString) {
		mongoClient = MongoClients.create(new ConnectionString(connectionString));
		MongoDatabase database = mongoClient.getDatabase(dbName);
		booksCollection = database.getCollection("books");
	}

	public void disconnect() {
		mongoClient.close();
	}

	public List<Item> selectAll() {
		FindIterable<Document> temp = booksCollection.find();
		LinkedList<Item> books = new LinkedList<Item>();
		for (Document document : temp) {
			books.add(new Book(document));
			System.out.println("found a doc: " + document.toString());
		}
		return books;
	}

	public void addOrUpdate(Book b) {
		Bson filter = createIdFilter(b.getId());
		Document update = b.createDocument();
		ReplaceOptions options = new ReplaceOptions().upsert(true);
		booksCollection.replaceOne(filter, update, options);
	}

	private Bson createIdFilter(Id id) {
		return Filters.eq("id", id.toString());
	}

	public void delete(Id id) {
		booksCollection.deleteOne(createIdFilter(id));
	}

}
