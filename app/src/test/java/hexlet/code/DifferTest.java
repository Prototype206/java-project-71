package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DifferTest {

    private static String file1JsonPath;
    private static String file2JsonPath;
    private static String file1YmlPath;
    private static String file2YmlPath;
    private static String file1NestedPath;
    private static String file2NestedPath;

    private static String expectedStylishFlat;
    private static String expectedPlainFlat;
    private static String expectedJsonFlat;
    private static String expectedStylishNested;
    private static String expectedPlainNested;
    private static String expectedJsonNested;

    @BeforeAll
    static void setUp() throws Exception {
        file1JsonPath = getFixturePath("file1.json");
        file2JsonPath = getFixturePath("file2.json");
        file1YmlPath = getFixturePath("file1.yml");
        file2YmlPath = getFixturePath("file2.yml");
        file1NestedPath = getFixturePath("file1-nested.json");
        file2NestedPath = getFixturePath("file2-nested.json");

        expectedStylishFlat = normalizeLineEndings(readFixture("expected-flat-stylish.txt"));
        expectedPlainFlat = normalizeLineEndings(readFixture("expected-flat-plain.txt"));
        expectedJsonFlat = readFixture("expected-flat-json.json");

        expectedStylishNested = normalizeLineEndings(readFixture("expected-nested-stylish.txt"));
        expectedPlainNested = normalizeLineEndings(readFixture("expected-nested-plain.txt"));
        expectedJsonNested = readFixture("expected-nested-json.json");
    }

    @Test
    void testJsonToStylish() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "stylish");
        assertEquals(expectedStylishNested, actual);
    }

    @Test
    void testJsonToPlain() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "plain");
        assertEquals(expectedPlainNested, actual);
    }

    @Test
    void testJsonToJson() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "json");
        assertJsonEquals(expectedJsonNested, actual);
    }

    @Test
    void testJsonToDefault() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath);
        assertEquals(expectedStylishNested, actual);
    }

    @Test
    void testYmlToStylish() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "stylish");
        assertEquals(expectedStylishFlat, actual);
    }

    @Test
    void testYmlToPlain() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "plain");
        assertEquals(expectedPlainFlat, actual);
    }

    @Test
    void testYmlToJson() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "json");
        assertJsonEquals(expectedJsonFlat, actual);
    }

    @Test
    void testYmlToDefault() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath);
        assertEquals(expectedStylishFlat, actual);
    }

    private static String getFixturePath(String fileName) {
        return Path.of("src", "test", "resources", fileName).toString();
    }

    private static String readFixture(String fileName) throws Exception {
        Path path = Path.of("src", "test", "resources", fileName);
        return Files.readString(path);
    }

    private static String normalizeLineEndings(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("\r\n", "\n").replace("\r", "\n");
    }

    private static void assertJsonEquals(String expected, String actual) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String normalizedExpected = normalizeLineEndings(expected);
        String normalizedActual = normalizeLineEndings(actual);
        assertEquals(
            mapper.readTree(normalizedExpected),
            mapper.readTree(normalizedActual)
        );
    }
}