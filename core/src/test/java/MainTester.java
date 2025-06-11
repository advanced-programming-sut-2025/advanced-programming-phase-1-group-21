import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import io.github.StardewValley.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTester {
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
		System.out.println("		------ " + info.getDisplayName() + " is started ------		");
	}

	@AfterEach
	void tearDownAfterEach(TestInfo info) {

		delete_users_json();
		System.out.println("		------ " + info.getDisplayName() + " is Done ------		");
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
	@Order(10000)
	void test0(int x) {
		System.out.println("Test " + x);
	}

	@Test
	@Order(1)
	void registerInvalidUsernameTest() throws IOException {
		String input =
				"""
						register -u A -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u AA -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u AAAAAAAAAAAAAAAAAAAAA -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u AAAAAAAAAAAAAAAAAAAAAA -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u &test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u ^test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u %test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u $test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u +test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u )test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u (test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u *test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u #test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u (test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u *test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u #test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u (test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u *test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u #test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u _test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u +test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u =test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u /test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u [test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u ]test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u {test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u }test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u \\test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u |test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						register -u @test -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						EXIT
						""";

		String output ="""
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						Invalid username
						""";
		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(2)
	void registerInvalidPasswordTest() throws IOException {
		String input =
				"""
						register -u ParsaTesting -p A A -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AA AA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAA AAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAA AAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAA AAAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAAA AAAAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAAAA AAAAAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAAAAA AAAAAAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAAAAAA AAAAAAAAA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p 1234567! 1234567! -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p 1234@675 1234@675 -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p AAAAAAAAA#@%#@% AAAAAAAAA#@%#@% -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa### Parsa### -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p P!@@ARSA P!@@ARSA -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p PaPa^^*^ PaPa^^*^ -n nickname -e parsa@gmail.com -g Male
						EXIT
						""";

		String output ="""
						Password too short
						Password too short
						Password too short
						Password too short
						Password too short
						Password too short
						Password too short
						Password must contain special characters
						Password must contain special characters
						Password must contain alphabet
						Password must contain alphabet
						Password must contain numbers
						Password must contain numbers
						Password must contain numbers
						Password must contain numbers
						""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}
	@Test
	@Order(3)
	void registerInvalidEmailTest() throws IOException {
		String input =
				"""
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa!@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e par#sa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e par@sa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e par+sa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e pars/a@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e .parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa.@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e -parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa-@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e _parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa_@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e par..sa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmailcom -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmail.c -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmai!l.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e john..doe@example.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e user@domain -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e user@domain..com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e user@domain.c -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e user@.com -g Male
						EXIT
						""";

		String output ="""
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						Invalid email format
						""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(4)
	void registerInvalidPasswordConfirmTest() throws IOException {
		String input =
				"""
						register -u ParsaTesting -p Parsa1234!@# dasd -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@ -n nickname -e parsa@gmail.com -g Male
						register -u ParsaTesting -p Parsa1234!@# arsa1234!@# -n nickname -e parsa@gmail.com -g Male
						EXIT
						""";

		String output ="""
						Password does not equal confirm password
						Password does not equal confirm password
						Password does not equal confirm password
						""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(5)
	void registerInvalidGenderTest() throws IOException {
		String input =
				"""
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmail.com -g Male1
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmail.com -g Mle
						register -u ParsaTesting -p Parsa1234!@# Parsa1234!@# -n nickname -e parsa@gmail.com -g Fem
						EXIT
						""";

		String output ="""
						There is no gender with that name
						There is no gender with that name
						There is no gender with that name
						""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(6)
	void loginUsernameNotFoundTest() throws IOException {
		String input =
				"""
						register -u Parsa1234 -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						pick question -q 1 -a test -c test
						go to login menu
						login -u Parsa12345 -p Parsa123!
						login -u Parsa12345 -p Parsa123! -stay-logged-in
						EXIT
						""";

		String output ="""
				User Registered Parsa1234 Parsa123!
				
				Questions:
				1. WHAT'S YOUR FAVORITE FOOD?
				2. WHAT'S YOUR FAVORITE COLOR?
				3. WHO'S YOUR FAVORITE SINGER?
				4. WHO'S YOUR FAVORITE ACTOR?
				5. WHERE'S YOUR FAVORITE CITY?
				6. WHERE'S YOUR FAVORITE COUNTRY?
				
				Answer successfully set!
				
				User not found
				User not found
				""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(7)
	void loginPasswordDoesntMatchTest() throws IOException {
		String input =
				"""
						register -u Parsa1234 -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						pick question -q 1 -a test -c test
						go to login menu
						login -u Parsa1234 -p Parsa1234!
						EXIT
						""";

		String output ="""
				User Registered Parsa1234 Parsa123!
				
				Questions:
				1. WHAT'S YOUR FAVORITE FOOD?
				2. WHAT'S YOUR FAVORITE COLOR?
				3. WHO'S YOUR FAVORITE SINGER?
				4. WHO'S YOUR FAVORITE ACTOR?
				5. WHERE'S YOUR FAVORITE CITY?
				6. WHERE'S YOUR FAVORITE COUNTRY?
				
				Answer successfully set!
				
				Password does not match with this username
				""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(8)
	void profileChangePasswordTest() throws IOException {
		String input =
				"""
						register -u Parsa1234 -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						pick question -q 1 -a test -c test
						go to login menu
						login 
						login -u Parsa1234 -p Parsa123!
						menu enter mainmenu
						menu enter profile
						change password -o dasd -n dasda
						change password -o A -n A
						change password -o AA -n AA
						change password -o AAA -n AAA
						change password -o AAAA -n AAAA
						change password -o AAAAA -n AAAAA
						change password -o AAAAAA -n AAAAAA
						change password -o AAAAAAA -n AAAAAAA
						change password -o AAAAAAAA -n AAAAAAAA
						change password -o AAAAAAAAA -n AAAAAAAAA
						change password -o 1234567! -n 1234567!
						change password -o 1234@675 -n 1234@675
						change password -o AAAAAAAAA#@%#@% -n AAAAAAAAA#@%#@%
						change password -o Parsa### -n Parsa###
						change password -o P!@@ARSA -n P!@@ARSA
						change password -o PaPa^^*^ -n PaPa^^*^
						EXIT
						""";

		String output ="""
				User Registered Parsa1234 Parsa123!
				
				Questions:
				1. WHAT'S YOUR FAVORITE FOOD?
				2. WHAT'S YOUR FAVORITE COLOR?
				3. WHO'S YOUR FAVORITE SINGER?
				4. WHO'S YOUR FAVORITE ACTOR?
				5. WHERE'S YOUR FAVORITE CITY?
				6. WHERE'S YOUR FAVORITE COUNTRY?
				
				Answer successfully set!
				
				invalid command
				User logged in successfully
				
				Now you are in profile menu
				Password too short
				Password too short
				Password too short
				Password too short
				Password too short
				Password too short
				Password too short
				Password too short
				Password must contain special characters
				Password must contain special characters
				Password must contain alphabet
				Password must contain alphabet
				Password must contain numbers
				Password must contain numbers
				Password must contain numbers
				Password must contain numbers
				""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(9)
	void profileChangeUsernameTest() throws IOException {
		String input =
				"""
						register -u Parsa1234 -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						pick question -q 1 -a test -c test
						go to login menu
						login 
						login -u Parsa1234 -p Parsa123!
						menu enter mainmenu
						menu enter profile
						change username -u A
						change username -u AA
						change username -u AAAAAAAAAAAAAAAAAAAAA
						change username -u AAAAAAAAAAAAAAAAAAAAAA
						change username -u &test
						change username -u ^test
						change username -u %test
						change username -u $test
						change username -u +test
						change username -u )test
						change username -u (test
						change username -u *test
						change username -u #test
						change username -u (test
						change username -u *test
						change username -u #test
						change username -u (test
						change username -u *test
						change username -u #test
						change username -u _test
						change username -u +test
						change username -u =test
						change username -u /test
						change username -u [test
						change username -u ]test
						change username -u {test
						change username -u }test
						change username -u \\test
						change username -u |test
						change username -u @test
						EXIT
						""";

		String output ="""
				User Registered Parsa1234 Parsa123!
				
				Questions:
				1. WHAT'S YOUR FAVORITE FOOD?
				2. WHAT'S YOUR FAVORITE COLOR?
				3. WHO'S YOUR FAVORITE SINGER?
				4. WHO'S YOUR FAVORITE ACTOR?
				5. WHERE'S YOUR FAVORITE CITY?
				6. WHERE'S YOUR FAVORITE COUNTRY?
				
				Answer successfully set!
				
				invalid command
				User logged in successfully
				
				Now you are in profile menu
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				Invalid username
				""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	@Test
	@Order(10)
	void profileChangeEmailTest() throws IOException {
		String input =
				"""
						register -u Parsa1234 -p Parsa123! Parsa123! -n nickname -e parsa@gmail.com -g Male
						pick question -q 1 -a test -c test
						go to login menu
						login 
						login -u Parsa1234 -p Parsa123!
						menu enter mainmenu
						menu enter profile
						change email -e parsa!@gmail.com
						change email -e par#sa@gmail.com
						change email -e par@sa@gmail.com
						change email -e par+sa@gmail.com
						change email -e pars/a@gmail.com
						change email -e .parsa@gmail.com
						change email -e parsa.@gmail.com
						change email -e -parsa@gmail.com
						change email -e parsa-@gmail.com
						change email -e _parsa@gmail.com
						change email -e parsa_@gmail.com
						change email -e par..sa@gmail.com
						change email -e parsa@gmailcom
						change email -e parsa@gmail.c
						change email -e parsa@gmai!l.com
						change email -e john..doe@example.com
						change email -e user@domain
						change email -e user@domain..com
						change email -e user@domain.c
						change email -e user@.com
						EXIT
						""";

		String output ="""
				User Registered Parsa1234 Parsa123!
				
				Questions:
				1. WHAT'S YOUR FAVORITE FOOD?
				2. WHAT'S YOUR FAVORITE COLOR?
				3. WHO'S YOUR FAVORITE SINGER?
				4. WHO'S YOUR FAVORITE ACTOR?
				5. WHERE'S YOUR FAVORITE CITY?
				6. WHERE'S YOUR FAVORITE COUNTRY?
				
				Answer successfully set!
				
				invalid command
				User logged in successfully
				
				Now you are in profile menu
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				Invalid email format
				""";

		String result = getOutput(input);

		// Assert
		assertEquals(output, result);
	}

	private void delete_users_json() {
		File file = new File("Users.json");

		if (file.exists())
			file.delete();
	}

	private String getOutput(String input) throws IOException {
		InputStream baseInput = System.in;
		PrintStream baseOutput = System.out;;
//		System.out.println("input: " + inputString);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outputStream));

		// Act
		Main main = new Main();
		main.create();
//		Main.create();

		// Assert
		System.setOut(baseOutput);
		System.setIn(baseInput);

		return outputStream.toString().replaceAll(" > ", "");
	}
}
