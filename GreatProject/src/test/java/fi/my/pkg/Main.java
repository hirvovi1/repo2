package fi.my.pkg;

import java.io.File;
import java.io.IOException;

import fi.my.pkg.service.BookService;
import fi.my.pkg.service.BookServiceImpl;

public class Main {

	private static final String DEFAULT_IMPORT_PATH = "." +
		File.separator + "build" +
		File.separator + "resources" +
		File.separator + "test" +
		File.separator + "import" + File.separator;

	public static void main(String[] args) {
		new Main(args);
	}

	public Main(String[] args) {
		BookService br;
		try {
			String path = getPath(args);
			createTestFilesForImport(path);
			br = BookServiceImpl.instance();
			br.importBooks(path);
			System.out.println("books in arcive: " + br.archiveSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getPath(String[] args) {
		return args.length < 2 ? DEFAULT_IMPORT_PATH : args[1];
	}

	public void createTestFilesForImport(String path) throws IOException {
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			new File(path + "pdf" + File.separator + isbn + ".pdf").createNewFile();
		}
	}

}
