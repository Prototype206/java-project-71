package hexlet.code.formatters;

import hexlet.code.DiffNode;
import java.util.List;
import java.util.Map;

public class StylishFormatter {
    
    private static final int INDENT_SIZE = 4;
    
    private StylishFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }
    
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
        String spaces = " ".repeat(INDENT_SIZE);
        String signSpaces = " ".repeat(INDENT_SIZE - 2);
        
        switch (node.getStatus()) {
            case UNCHANGED:
                return spaces + node.getKey() + ": " + formatValue(node.getNewValue()) + "\n";
            case ADDED:
                return signSpaces + "+ " + node.getKey() + ": " + formatValue(node.getNewValue()) + "\n";
            case REMOVED:
                return signSpaces + "- " + node.getKey() + ": " + formatValue(node.getOldValue()) + "\n";
            case CHANGED:
                return signSpaces + "- " + node.getKey() + ": " + formatValue(node.getOldValue()) + "\n"
                     + signSpaces + "+ " + node.getKey() + ": " + formatValue(node.getNewValue()) + "\n";
            default:
                return "";
        }
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