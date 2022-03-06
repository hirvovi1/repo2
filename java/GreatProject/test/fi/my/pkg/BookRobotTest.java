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
		robot.addBookToArchive(new ClassicBook(1, "978-3-16-148410-0"));
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
		robot.addBookToArchive(new Book(new Id(1), "978-3-16-148410-0"));
		Book b = new Book(new Id(2), "978-3-16-148410-0");
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
	void testFindABookFromArchive() throws Exception {
		robot.addBookToArchive(new Book(new Id(1), "978-3-16-148410-0"));
		robot.addBookToArchive(new Book(new Id(2), "978-3-16-148410-0"));
		robot.addBookToArchive(new Book(new Id(3), "978-3-16-148410-0"));
		robot.addBookToArchive(new Book(new Id(4), "978-1-56581-231-4"));
		
		Assertions.assertEquals(4, robot.archiveSize());
		Book found = robot.findBook("978-1-56581-231-4");
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getIsbn(), "978-1-56581-231-4");
		Assertions.assertEquals(found.getId(), new Id(4));
	}
}
