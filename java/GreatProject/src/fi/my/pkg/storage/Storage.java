package fi.my.pkg.storage;

import java.lang.reflect.InvocationTargetException;
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

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.PdfBook;
import fi.my.pkg.dependents.PdfFileNotFoundException;

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
		// TODO bug, filter classic books
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

	public List<Book> selectPdfBooks() throws Exception {
		return findBooks("pdfilename", PdfBook.class);
	}

	private List<Book> findBooks(String fieldname, Class<? extends Book> c) 
			throws PdfFileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		Bson filter = Filters.exists(fieldname);
		FindIterable<Document> temp = booksCollection.find(filter);
		
		LinkedList<Book> books = new LinkedList<Book>();
		for (Document document : temp) {
			Book book = c.getConstructor(Document.class).newInstance(document);
			books.add(book);
			System.out.println("found a doc: " + document.toString());
		}
		return books;
	}

	public List<Book> selectAudioBooks() throws Exception {
		return findBooks("audiofilename", AudioBook.class);
	}

}
