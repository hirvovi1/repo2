package fi.my.pkg;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testIdNotEqualsId() {
			Assertions.assertFalse(new Id(1).equals(new Id(2)));
	}
	
	@Test
	final void testIdEqualsIdWrongType() {
			Assertions.assertTrue(new Id(1).equals(new Id(1)));
	}
	
	@Test
	final void testIdEqualsNotWrongType() {
			Assertions.assertFalse(new Id(1).equals(Integer.valueOf(1)));
	}

}
