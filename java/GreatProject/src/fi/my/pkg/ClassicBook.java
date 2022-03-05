package fi.my.pkg;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class ClassicBook extends Book {

	private final List<Page> pages = new LinkedList<Page>();

	public ClassicBook(Id id, String isbn) {
		super(id, isbn);
	}

	public ClassicBook(int id, String isbn) {
		this(new Id(id), isbn);
	}

	@Override
	public Document createDocument() {
		Document d = new Document();
		return d.append("id", getId().toString()).append("isbn", getIsbn()).append("pages", createPagesToDocument());
	}

	private List<Document> createPagesToDocument() {
		List<Document> pageDocs = new LinkedList<Document>();
		for (Page page : pages) {
			Document pageDoc = new Document("number", page.getNumber());
			pageDocs.add(pageDoc.append("lines", page.getLines()));
		}
		return pageDocs;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void printPages() {
		for (Page page : pages) {
			page.print();
		}
	}
}
