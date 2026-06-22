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
    private static String expectedStylish;
    private static String expectedPlain;
    private static String expectedJson;

    @BeforeAll
    static void setUp() throws Exception {
        file1JsonPath = getFixturePath("file1.json");
        file2JsonPath = getFixturePath("file2.json");
        file1YmlPath = getFixturePath("file1.yml");
        file2YmlPath = getFixturePath("file2.yml");
        file1NestedPath = getFixturePath("file1-nested.json");
        file2NestedPath = getFixturePath("file2-nested.json");

        expectedStylish = normalizeLineEndings(readFixture("expected-nested.txt"));
        expectedPlain = normalizeLineEndings(readFixture("expected-plain.txt"));
        expectedJson = readFixture("expected-json.json");  // ← НЕ нормализуем
    }

    @Test
    void testJsonToStylish() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "stylish");
        assertEquals(expectedStylish, normalizeLineEndings(actual));
    }

    @Test
    void testJsonToPlain() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "plain");
        assertEquals(expectedPlain, normalizeLineEndings(actual));
    }

    @Test
    void testJsonToJson() throws Exception {
        String actual = Differ.generate(file1NestedPath, file2NestedPath, "json");
        assertJsonEquals(expectedJson, actual);
    }

    @Test
    void testJsonToDefault() throws Exception {
        String actualDefault = Differ.generate(file1NestedPath, file2NestedPath);
        String actualStylish = Differ.generate(file1NestedPath, file2NestedPath, "stylish");
        assertEquals(normalizeLineEndings(actualStylish), normalizeLineEndings(actualDefault));
    }

    @Test
    void testYmlToStylish() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "stylish");
        String expected = Differ.generate(file1JsonPath, file2JsonPath, "stylish");
        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual));
    }

    @Test
    void testYmlToPlain() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "plain");
        String expected = Differ.generate(file1JsonPath, file2JsonPath, "plain");
        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual));
    }

    @Test
    void testYmlToJson() throws Exception {
        String actual = Differ.generate(file1YmlPath, file2YmlPath, "json");
        String expected = Differ.generate(file1JsonPath, file2JsonPath, "json");
        assertJsonEquals(expected, actual);
    }

    @Test
    void testYmlToDefault() throws Exception {
        String actualDefault = Differ.generate(file1YmlPath, file2YmlPath);
        String actualStylish = Differ.generate(file1YmlPath, file2YmlPath, "stylish");
        assertEquals(normalizeLineEndings(actualStylish), normalizeLineEndings(actualDefault));
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
        // Нормализуем только для парсинга JSON
        String normalizedExpected = normalizeLineEndings(expected);
        String normalizedActual = normalizeLineEndings(actual);
        assertEquals(
            mapper.readTree(normalizedExpected),
            mapper.readTree(normalizedActual)
        );
    }
}