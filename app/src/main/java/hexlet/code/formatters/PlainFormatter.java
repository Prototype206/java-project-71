package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;
import java.util.Map;

public class PlainFormatter {

    private PlainFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder();

        for (DiffNode node : diff) {
            String line = formatNode(node);
            if (line != null && !line.isEmpty()) {
                result.append(line).append("\n");
            }
        }

        if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    private static String formatNode(DiffNode node) {
        String key = node.getKey();

        switch (node.getStatus()) {
            case ADDED:
                return "Property '" + key + "' was added with value: " + formatValue(node.getNewValue());

            case REMOVED:
                return "Property '" + key + "' was removed";

            case CHANGED:
                return "Property '" + key + "' was updated. From " + formatValue(node.getOldValue())
                     + " to " + formatValue(node.getNewValue());

            case UNCHANGED:
                return null;

            default:
                throw new IllegalStateException("Unknown node status: " + node.getStatus());
        }
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof Boolean) {
            return value.toString().toLowerCase();
        }
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        return String.valueOf(value);
    }
}