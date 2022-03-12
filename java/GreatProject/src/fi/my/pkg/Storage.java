package fi.my.pkg;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.PdfBook;

public class Storage {

	private MongoCollection<Document> booksCollection;
	private MongoClient mongoClient;
	private final String dbName;

	public Storage() {
		this.dbName = "bookdb";
		connect("mongodb://localhost:27017/?retryWrites=false");
	}
	
	public Storage(String connectionString, String database) {
		this.dbName = database;
		connect(connectionString);
	}

	public void connect(String connectionString) {
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(new ConnectionString(connectionString))
		        .serverApi(ServerApi.builder()
		            .version(ServerApiVersion.V1)
		            .build())
		        .build();
		mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		booksCollection = database.getCollection("books");
	}

	public void disconnect() {
		mongoClient.close();
	}

	public List<Book> selectClassicBooks() {
		FindIterable<Document> temp = booksCollection.find();
		LinkedList<Book> books = new LinkedList<Book>();
		for (Document document : temp) {
			books.add(new ClassicBook(document));
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
	
	public void deleteAll() {
		booksCollection.deleteMany(Filters.empty());
	}

	public List<Book> selectPdfBooks() {
		Bson filter = Filters.exists("pdfilename");
		FindIterable<Document> temp = booksCollection.find(filter);
		
		LinkedList<Book> books = new LinkedList<Book>();
		for (Document document : temp) {
			books.add(new PdfBook(document));
			System.out.println("found a doc: " + document.toString());
		}
		return books;
	}

}
