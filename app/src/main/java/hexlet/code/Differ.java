package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import hexlet.code.formatters.Formatter;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        String content1 = Files.readString(Path.of(filepath1));
        String content2 = Files.readString(Path.of(filepath2));

        String format1 = getExtension(filepath1);
        String format2 = getExtension(filepath2);

        Map<String, Object> data1 = Parser.parse(content1, format1);
        Map<String, Object> data2 = Parser.parse(content2, format2);

        List<DiffNode> diff = DifferBuilder.buildDiff(data1, data2);

        return Formatter.format(diff, format);
    }

    private static String getExtension(String filepath) throws Exception {
        int lastDot = filepath.lastIndexOf('.');
        if (lastDot == -1) {
            throw new Exception("Cannot determine file format: " + filepath);
        }
        return filepath.substring(lastDot + 1).toLowerCase();
    }
}