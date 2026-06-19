package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;

public class Formatter {

    private Formatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String format(List<DiffNode> diff, String format) throws Exception {
        if (format == null || format.isEmpty() || "default".equals(format)) {
            format = "stylish";
        }

        return switch (format) {
            case "stylish" -> StylishFormatter.format(diff);
            case "plain" -> PlainFormatter.format(diff);
            case "json" -> JsonFormatter.format(diff);
            default -> throw new IllegalArgumentException(
                "Unsupported format: '" + format + "'. Available formats: stylish, plain, json"
            );
        };
    }
}