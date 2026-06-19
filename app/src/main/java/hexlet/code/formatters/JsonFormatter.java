package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffNode;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class JsonFormatter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String format(List<DiffNode> diff) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        for (DiffNode node : diff) {
            Map<String, Object> nodeMap = new LinkedHashMap<>();
            nodeMap.put("key", node.getKey());
            nodeMap.put("status", node.getStatus().toString().toLowerCase());

            switch (node.getStatus()) {
                case ADDED:
                    nodeMap.put("value", node.getNewValue());
                    break;
                case REMOVED:
                    nodeMap.put("value", node.getOldValue());
                    break;
                case CHANGED:
                    nodeMap.put("oldValue", node.getOldValue());
                    nodeMap.put("newValue", node.getNewValue());
                    break;
                case UNCHANGED:
                    nodeMap.put("value", node.getNewValue());
                    break;
                default:
                    throw new IllegalStateException("Unknown status: " + node.getStatus());
            }

            result.add(nodeMap);
        }

        return mapper.writeValueAsString(result);
    }
}