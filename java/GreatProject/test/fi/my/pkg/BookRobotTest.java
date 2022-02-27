package fi.my.pkg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookRobotTest {
	
	private BookRobot robot;
		
	@BeforeEach
	public void setUp() throws Exception {
		robot = new BookRobot();
	}

	
	@Test
	public void testAddBookToArchive() {
		robot.addBookToArchive(new Book());
		Book b = robot.getNewestBookFromArchive();
		assertNotNull(b);
	}
	
	@Test
	public final void testAddNullBookToArchive() {
		assertThrows(NullPointerException.class, () -> {
			robot.addBookToArchive(null);
		});
	}

	@Test
	public final void testGetNewestBookFromArchive() {
		robot.addBookToArchive(new Book());
		Book b = new Book();
		robot.addBookToArchive(b);
		assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	public final void testSaveArchive() {
		robot.addBookToArchive(new Book());
		robot.saveArchive();
	}

}
