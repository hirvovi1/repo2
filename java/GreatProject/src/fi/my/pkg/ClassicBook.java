package fi.my.pkg;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class ClassicBook extends Book {

	private final List<Page> pages = new LinkedList<Page>();
	
	public ClassicBook(Document document) {
		super(id(document), isbn(document), title(document));
	}

	public ClassicBook(int id, String isbn, String title) {
		super(id, isbn, title);
	}

	@Override
	public Document createDocument() {
		Document d = super.createDocument();
		d.append("pages", createPagesToDocument());
		return d;
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
