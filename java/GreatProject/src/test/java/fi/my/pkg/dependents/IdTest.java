package fi.my.pkg.dependents;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdTest {


	@Test
	final void testIdNotEqualsId() {
			Assertions.assertFalse(new Id(1).equals(new Id(2)));
	}
	
	@Test
	final void testIdEqualsIdWrongType() {
			Assertions.assertTrue(new Id(1).equals(new Id(1)));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	final void testIdEqualsNotWrongType() {
			Assertions.assertFalse(new Id(1).equals(1));
	}

	@Test
	void testIdEqualsId() throws Exception {
		Assertions.assertEquals(new Id(1).hashCode(), new Id(1).hashCode());
		Assertions.assertEquals(new Id(1).asInt(), new Id(1).asInt());
		Assertions.assertEquals(new Id(1).toString(), new Id(1).toString());
	}

	@Test
	void testStringConstructor() throws Exception {
		Assertions.assertEquals(new Id("1"), new Id(1));
	}

}
