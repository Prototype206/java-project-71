package hexlet.code;

import java.util.List;
import java.util.Map;

public class StylishFormatter {

    private static final int INDENT_SIZE = 4;

    public static String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (DiffNode node : diff) {
            result.append(formatNode(node));
        }

        result.append("}");
        return result.toString();
    }

    private static String formatNode(DiffNode node) {
        StringBuilder sb = new StringBuilder();

        switch (node.getStatus()) {
            case UNCHANGED:
                sb.append(" ".repeat(INDENT_SIZE))
                  .append(node.getKey())
                  .append(": ")
                  .append(formatValue(node.getNewValue()))
                  .append("\n");
                break;

            case ADDED:
                sb.append(" ".repeat(INDENT_SIZE - 2))
                  .append("+ ")
                  .append(node.getKey())
                  .append(": ")
                  .append(formatValue(node.getNewValue()))
                  .append("\n");
                break;

            case REMOVED:
                sb.append(" ".repeat(INDENT_SIZE - 2))
                  .append("- ")
                  .append(node.getKey())
                  .append(": ")
                  .append(formatValue(node.getOldValue()))
                  .append("\n");
                break;

            case CHANGED:
                sb.append(" ".repeat(INDENT_SIZE - 2))
                  .append("- ")
                  .append(node.getKey())
                  .append(": ")
                  .append(formatValue(node.getOldValue()))
                  .append("\n");
                sb.append(" ".repeat(INDENT_SIZE - 2))
                  .append("+ ")
                  .append(node.getKey())
                  .append(": ")
                  .append(formatValue(node.getNewValue()))
                  .append("\n");
                break;
        }

        return sb.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Boolean) {
            return value.toString().toLowerCase();
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof List || value instanceof Map) {
            return value.toString();
        }
        return String.valueOf(value);
    }
}