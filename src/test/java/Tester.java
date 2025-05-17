import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tester {
	private static int numberOfTestCases;
	private static String testCaseDirectory = "src/test/resources/testcases/";

	@BeforeAll
	static void setup() {
		File folder = new File(testCaseDirectory);

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			int fileCount = 0;

			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						fileCount++;
					}
				}
				numberOfTestCases = fileCount / 2;
			} else {
				throw new Error("No files found in " + testCaseDirectory);
			}
		} else {
			throw new Error("Can't find " + testCaseDirectory);
		}
		System.out.println("################ Starting Tests ################");
	}

	@AfterAll
	static void tearDown() {
		System.out.println("################ Ending Tests ################");
	}

	@BeforeEach
	void setupBeforeEach(TestInfo info) {
		System.out.println("		------ test: " + info.getDisplayName() + " is started ------		");
	}

	@AfterEach
	void tearDownAfterEach(TestInfo info) {
		System.out.println("		------ test " + info.getDisplayName() + " is Done ------		");
	}

	static List <TestCase> provideFileTestCases() {
		List<TestCase> testCases = new ArrayList<>();
		for (int i = 0; i < numberOfTestCases; i++) {
			testCases.add(new TestCase(testCaseDirectory + "input" + (i + 1) + ".txt",
					testCaseDirectory + "output" + (i + 1) + ".txt"));
		}
		return testCases;
	}

	private static class TestCase {
		private final String inputFilePath;
		private final String outputFilePath;

		public TestCase(String inputFilePath, String outputFilePath) {
			this.inputFilePath = inputFilePath;
			this.outputFilePath = outputFilePath;
		}

		public String inputFilePath() {
			return inputFilePath;
		}

		public String outputFilePath() {
			return outputFilePath;
		}
	}


	@Disabled
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	@Order(1)
	void test0(int x) {
		System.out.println("Test " + x);
	}

	@Disabled
	@ParameterizedTest
	@MethodSource("provideFileTestCases")
	@Order(2)
	@DisplayName("This is Second test's DisplayName")
	void test(TestCase testcase) throws IOException {
		InputStream baseInput = System.in;
		PrintStream baseOutput = System.out;

		System.out.println("input address: " + testcase.inputFilePath());
		System.out.println("input address: " + testcase.outputFilePath());

		String inputString = Files.readString(Path.of(testcase.inputFilePath()));
//		System.out.println("input: " + inputString);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		System.setIn(new ByteArrayInputStream(inputString.getBytes()));
		System.setOut(new PrintStream(outputStream));

		// Act
		Main.main(null);


		String result = outputStream.toString();

		// Assert
		System.setOut(baseOutput);
		System.setIn(baseInput);


		delete_users_json();
	}

	private void delete_users_json() {
		File file = new File("Users.json");

		if (file.exists()) {
			if (file.delete()) {
				System.out.println("File deleted successfully.");
			} else {
				System.out.println("Failed to delete the file.");
			}
		} else {
			System.out.println("File does not exist.");
		}
	}
}
