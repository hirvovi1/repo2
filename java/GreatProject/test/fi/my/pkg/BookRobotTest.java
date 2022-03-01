package fi.my.pkg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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
		robot.addBookToArchive(new Book(new Id(1)));
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
		robot.addBookToArchive(new Book(new Id(1)));
		Book b = new Book(new Id(2));
		robot.addBookToArchive(b);
		assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	public final void testSaveArchive() {
		Book b = new Book(new Id(1));
		robot.addBookToArchive(b);
		robot.saveArchive();
		verify(mockStorage).addOrUpdate(ArgumentMatchers.eq(b));
	}

}
