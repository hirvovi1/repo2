package fi.my.pkg.dependents;

import java.io.File;

import org.bson.Document;

public class AudioBook extends Book {
	
	private File soundFile;

	public AudioBook(int id, String isbn, String title, String fileName) {
		super(id, isbn, title);
		soundFile = new File(fileName);
		if (!soundFile.exists()) throw new IllegalArgumentException("file not found");
	}
	
	public AudioBook(Document doc) {
		this(id(doc), isbn(doc), title(doc), filename(doc));
	}

	private static String filename(Document document) {
		return document.getString("filename");
	}

	@Override
	public Document createDocument() {
		Document d = super.createDocument();
		d.append("filename", soundFile.getName());
		return d;
	}
	
	public File getSoundFile() {
		return soundFile;
	}

}
