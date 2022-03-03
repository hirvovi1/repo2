package fi.my.pkg;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfBook extends Book {

	private PDDocument pdf;

	public PdfBook(Id id, String isbn) {
		super(id, isbn);
	}

	public PDDocument getPdf() {
		return pdf;
	}

	public void setPdf(PDDocument pdf) {
		this.pdf = pdf;
	}

}
