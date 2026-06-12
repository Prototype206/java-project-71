package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DifferTest {

    private static String file1Path;
    private static String file2Path;
    private static String expectedResult;

    @BeforeAll
    static void setUp() {
        file1Path = getFixturePath("file1.json");
        file2Path = getFixturePath("file2.json");
        expectedResult = normalizeLineEndings(readFixture("expected.txt"));
    }

    @Test
    void testGenerate() throws Exception {
        String actual = normalizeLineEndings(Differ.generate(file1Path, file2Path));
        assertNotNull(actual);
        assertEquals(expectedResult, actual);
    }

    @Test
    void testGenerateWithSameFiles() throws Exception {
        String actual = Differ.generate(file1Path, file1Path);
        assertNotNull(actual);
    }

    private static String getFixturePath(String fileName) {
        return Path.of("src", "test", "resources", fileName).toString();
    }

    private static String readFixture(String fileName) {
        try {
            Path path = Path.of("src", "test", "resources", fileName);
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read fixture: " + fileName, e);
        }
    }

    @Test
    void testGenerateYaml() throws Exception {
        String file1Yml = getFixturePath("file1.yml");
        String file2Yml = getFixturePath("file2.yml");
        String expected = readFixture("expected.yml");

        String actual = Differ.generate(file1Yml, file2Yml);

        assertEquals(normalizeLineEndings(expected), normalizeLineEndings(actual));
    }

    private static String normalizeLineEndings(String str) {
        return str.replace("\r\n", "\n").replace("\r", "\n");
    }
}