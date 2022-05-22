package fi.my.pkg.dependents;

public class PdfFileNotFoundException extends java.io.FileNotFoundException {
	
	private static final long serialVersionUID = 1L;
	

	public PdfFileNotFoundException(String message) {
		super(message);
	}
}
