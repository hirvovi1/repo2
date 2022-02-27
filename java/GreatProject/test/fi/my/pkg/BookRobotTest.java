package fi.my.pkg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookRobotTest {
	
	BookRobot robot;
		
	@BeforeEach
	void setUp() throws Exception {
		robot = new BookRobot();
	}

	
	@Test
	final void testAddBookToArchive() {
		robot.addBookToArchive(new Book());
		Book b = robot.getNewestBookFromArchive();
		assertNotNull(b);
	}
	
	@Test
	final void testAddNullBookToArchive() {
		assertThrows(NullPointerException.class, () -> {
			robot.addBookToArchive(null);
		});
	}

	@Test
	final void testGetNewestBookFromArchive() {
		robot.addBookToArchive(new Book());
		Book b = new Book();
		robot.addBookToArchive(b);
		assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	final void testSaveArchive() {
		robot.addBookToArchive(new Book());
		robot.saveArchive();
		fail("Not yet implemented"); // TODO
	}

}
