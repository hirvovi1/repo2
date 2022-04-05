package fi.my.pkg;

import java.io.File;
import java.io.IOException;

import fi.my.pkg.service.BookService;
import fi.my.pkg.service.BookServiceImpl;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		BookService br;
		try {
			createTestFilesForImport();
			br = BookServiceImpl.instance();
			br.importBooks();
			System.out.println("books in arcive: " + br.archiveSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTestFilesForImport() throws IOException {
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			new File("./test/import/pdf/" + isbn + ".pdf").createNewFile();
		}
	}

}
