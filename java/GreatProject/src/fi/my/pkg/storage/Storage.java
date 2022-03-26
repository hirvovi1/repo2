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
import com.mongodb.client.result.UpdateResult;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.PdfBook;
import fi.my.pkg.dependents.PdfFileNotFoundException;

public class Storage {

	private MongoCollection<Document> booksCollection;
	private MongoCollection<Document> idCollection;
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
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		booksCollection = database.getCollection("books");
		idCollection = database.getCollection("ids");
		IdUtil.INSTANCE.set(this);
	}

	public void disconnect() {
		mongoClient.close();
	}

	public List<Book> selectClassicBooks() throws Exception {
		return findBooks("pages", ClassicBook.class);
	}

	public void addOrUpdate(Book b) {
		Id id = createIdIfNecessary(b.getId());
		b.setId(id);
		Bson filter = createIdFilter(id);
		Document update = b.createDocument();
		ReplaceOptions options = new ReplaceOptions().upsert(true);
		UpdateResult status = booksCollection.replaceOne(filter, update, options);
		System.out.println("addOrUpdate status: " + status);
	}

	private Bson createIdFilter(Id id) {
		return Filters.eq("id", id.toString());
	}

	private Id createIdIfNecessary(Id id) {
		if (id == null) {
			id = new Id(nextId());
		}
		return id;
	}

	private int nextId() {
		return IdUtil.INSTANCE.next();
	}

	public void delete(Isbn isbn) {
		booksCollection.deleteOne(createIsbnFilter(isbn));
	}
	
	public void delete(Id id) {
		booksCollection.deleteOne(createIdFilter(id));
	}

	private Bson createIsbnFilter(Isbn isbn) {
		return Filters.eq("isbn", isbn.getIsbn());
	}

	public void deleteAll() {
		booksCollection.deleteMany(Filters.empty());
		idCollection.deleteMany(Filters.empty());
	}

	public List<Book> selectPdfBooks() throws Exception {
		return findBooks("pdfilename", PdfBook.class);
	}

	private List<Book> findBooks(String fieldname, Class<? extends Book> clazz)
			throws PdfFileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Bson filter = Filters.exists(fieldname);
		FindIterable<Document> docs = booksCollection.find(filter);

		LinkedList<Book> books = new LinkedList<Book>();
		for (Document document : docs) {
			Book book = clazz.getConstructor(Document.class).newInstance(document);
			books.add(book);
			System.out.println("found a doc: " + document.toString());
		}
		return books;
	}

	public List<Book> selectAudioBooks() throws Exception {
		return findBooks("audiofilename", AudioBook.class);
	}

	public int getId() {
		final Document first = idCollection.find().first();
		if (first == null) {
			return 0;
		}
		return first.getInteger("id");
	}

	public void saveId(int id) {
		Document update = new Document();
		update.put("id", id);
		ReplaceOptions options = new ReplaceOptions().upsert(true);
		UpdateResult status = idCollection.replaceOne(Filters.empty(), update, options);
		System.out.println("saveId: " + status);
	}

}
