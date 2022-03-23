package fi.my.pkg.dependents;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.my.pkg.TestDataUtil;

class PageTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void test() {
		Page p = new Page(1, TestDataUtil.getLines());
		p.print();
	}

}
