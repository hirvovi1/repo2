package fi.my.pkg;

import java.io.File;
import java.io.IOException;

import fi.my.pkg.service.BookService;
import fi.my.pkg.storage.Storage;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		BookService br;
		try {
			createTestFilesForImport();
			br = new BookService(new Storage());
			br.importBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTestFilesForImport() throws IOException {
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			String s = "./test/import/pdf/" + isbn + ".pdf";
			new File(s).createNewFile();
		}
	}

}
