package fi.my.pkg.dependents;

import java.io.File;

import org.bson.Document;

public class AudioBook extends Book {
	
	private File soundFile;
	
	public AudioBook(String isbn, String title, String fileName) 
			throws SoundFileNotFoundException 
	{
		super(isbn, title);
		soundFile = new File(fileName);
		if (!soundFile.exists()) throw new SoundFileNotFoundException("file not found");
	}
	
	private AudioBook(int id, String isbn, String title, String fileName) 
			throws SoundFileNotFoundException 
	{
		super(id, isbn, title);
		soundFile = new File(fileName);
		if (!soundFile.exists()) throw new SoundFileNotFoundException("file not found");
	}
	
	public AudioBook(Document doc) throws SoundFileNotFoundException {
		this(id(doc), isbn(doc), title(doc), audiofilename(doc));
	}

	private static String audiofilename(Document document) {
		return document.getString("audiofilename");
	}

	@Override
	public Document createDocument(String id) {
		Document d = super.createDocument(id);
		d.append("audiofilename", soundFile.getName());
		return d;
	}
	
	public File getSoundFile() {
		return soundFile;
	}

}
