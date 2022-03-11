package fi.my.pkg;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookRobotTest {

	@Mock
	Storage mockStorage;

	private BookRobot robot;

	@BeforeEach
	public void setUp() throws Exception {
		robot = new BookRobot(mockStorage);
	}

	@Test
	public void testAddBookToArchive() {
		robot.addBookToArchive(new ClassicBook(1, "978-3-16-148410-0", "title"));
		Book b = robot.getNewestBookFromArchive();
		Assertions.assertNotNull(b);
	}

	@Test
	public final void testAddNullBookToArchive() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			robot.addBookToArchive(null);
		});
	}

	@Test
	public final void testGetNewestBookFromArchive() {
		robot.addBookToArchive(new Book(1, "978-3-16-148410-0", "title"));
		Book b = new Book(2, "978-3-16-148410-0", "title");
		robot.addBookToArchive(b);
		Assertions.assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	public final void testSaveArchive() {
		ClassicBook b = new ClassicBook(1, "978-1-56581-231-4", "Crime and punishment");
		TestDataUtil.createPages(b.getPages());
		robot.addBookToArchive(b);
		robot.saveArchive();
		verify(mockStorage).addOrUpdate(ArgumentMatchers.eq(b));
	}

	@Test
	void testFindABookFromArchiveWithIsbn() throws Exception {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(id++, isbn, "test.pdf", "title"));
		}
		Assertions.assertEquals(30, robot.archiveSize());
		Book found = robot.findBook(new Isbn("978-1-56581-231-4"));
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getIsbn(), new Isbn("978-1-56581-231-4"));
		Assertions.assertEquals(found.getId(), new Id(23));
	}
	
	@Test
	void testFindABookFromArchiveWithTitle() throws Exception {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(id, isbn, "test.pdf", "title" + id));
			id++;
		}
		Assertions.assertEquals(30, robot.archiveSize());
		Title titleToFind = new Title("title9");
		Book found = robot.findBook(titleToFind );
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getTitle(), titleToFind);
		Assertions.assertEquals(found.getId(), new Id(9));
		Assertions.assertEquals(found.getIsbn().getIsbn(), "9789581054046");
	}
}
