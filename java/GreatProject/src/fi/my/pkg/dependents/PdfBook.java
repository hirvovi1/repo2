package fi.my.pkg.dependents;

import java.io.File;

import org.bson.Document;

public class PdfBook extends Book {

	private final File pdfFile;

	public PdfBook(int id, String isbn, String fileName, String title) 
			throws PdfFileNotFoundException 
	{
		super(id, isbn, title);
		pdfFile = new File(fileName);
		if (!pdfFile.exists()) throw new PdfFileNotFoundException("file not found");
	}

	public PdfBook(Document document) throws PdfFileNotFoundException {
		this(id(document), isbn(document), filename(document), title(document));
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
