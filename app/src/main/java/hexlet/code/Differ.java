package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;

public class Differ {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String generate(String filepath1, String filepath2) throws Exception {
        Map<String, Object> data1 = readFile(filepath1);
        Map<String, Object> data2 = readFile(filepath2);

        return generateStylish(data1, data2);
    }

    private static Map<String, Object> readFile(String filepath) throws Exception {
        Path path = Path.of(filepath);
        String content = Files.readString(path);
        return mapper.readValue(content, Map.class);
    }

    private static String generateStylish(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        List<String> lines = new ArrayList<>();
        lines.add("{");

        for (String key : allKeys) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);

            if (inFirst && inSecond) {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                if (value1.equals(value2)) {
                    lines.add("    " + key + ": " + formatValue(value1));
                } else {
                    lines.add("  - " + key + ": " + formatValue(value1));
                    lines.add("  + " + key + ": " + formatValue(value2));
                }
            } else if (inFirst && !inSecond) {
                lines.add("  - " + key + ": " + formatValue(data1.get(key)));
            } else {
                lines.add("  + " + key + ": " + formatValue(data2.get(key)));
            }
        }

        lines.add("}");
        return String.join("\n", lines);
    }

    private static String formatValue(Object value) {
        if (value instanceof Boolean) {
            return value.toString().toLowerCase();
        } else if (value instanceof String) {
            return (String) value;
        }
        return String.valueOf(value);
    }
}