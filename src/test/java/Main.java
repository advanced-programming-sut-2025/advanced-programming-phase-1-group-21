import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Main {

	@BeforeAll
	static void setup() {
		System.out.println("Starting Tests...");
	}

	@AfterAll
	static void tearDown() {
		System.out.println("Ending Tests...");
	}

	@BeforeEach
	void setupBeforeEach(TestInfo info) {
		System.out.println("Starting test " + info.getDisplayName());
	}

	@AfterEach
	void tearDownAfterEach() {
		System.out.println("Ending test...");
	}


	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	@Order(1)
	void test0(int x) {
		System.out.println("This is supposed to be the first test with number " + x);
	}

	@Test
	@Order(2)
	@DisplayName("This is Second test's DisplayName")
	void test() {
		System.out.println("This is supposed to be the second test");
	}

	@Test
	@Order(3)
	void test2() {
		System.out.println("This is supposed to be the third test");
	}
}
