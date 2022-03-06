package fi.my.pkg;

import java.io.File;

import org.bson.Document;

public class PdfBook extends Book {

	private final File pdfFile;

	public PdfBook(Id id, String isbn, String fileName) {
		super(id, isbn);
		pdfFile = new File(fileName);
		validate();
	}

	public File getPdfFile() {
		return pdfFile;
	}

	@Override
	protected void validate() {
		super.validate();
		if (!pdfFile.exists()) throw new IllegalArgumentException("file not found");
	}

	@Override
	public Document createDocument() {
		Document d = super.createDocument();
		d.append("pdfilename", pdfFile.getName());
		return d;
	}

}
