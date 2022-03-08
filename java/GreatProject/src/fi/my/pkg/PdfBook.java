package fi.my.pkg;

import java.io.File;

import org.bson.Document;

public class PdfBook extends Book {

	private final File pdfFile;

	public PdfBook(Id id, String isbn, String fileName) {
		super(id, isbn);
		pdfFile = new File(fileName);
		if (!pdfFile.exists()) throw new IllegalArgumentException("file not found");
	}
	
	public PdfBook(int id, String isbn, String fileName) {
		this(new Id(id), isbn, fileName);
	}

	public PdfBook(Document document) {
		this(id(document), isbn(document), filename(document));
	}


	private static String filename(Document document) {
		return document.getString("pdfilename");
	}

	public File getPdfFile() {
		return pdfFile;
	}

	@Override
	public Document createDocument() {
		Document d = super.createDocument();
		d.append("pdfilename", pdfFile.getName());
		return d;
	}

}
