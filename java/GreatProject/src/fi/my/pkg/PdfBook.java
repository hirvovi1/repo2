package fi.my.pkg;

import java.io.File;

import org.bson.Document;

public class PdfBook extends Book {

	private final File pdfFile;

	public PdfBook(int id, String isbn, String fileName) {
		super(new Id(id), isbn);
		pdfFile = new File(fileName);
		if (!pdfFile.exists()) throw new IllegalArgumentException("file not found");
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
