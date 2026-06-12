package hexlet.code;

import java.util.List;
import java.util.Map;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> data1 = Parser.parse(filepath1);
        Map<String, Object> data2 = Parser.parse(filepath2);

        List<DiffNode> diff = DifferBuilder.buildDiff(data1, data2);

        return StylishFormatter.format(diff);
    }
}