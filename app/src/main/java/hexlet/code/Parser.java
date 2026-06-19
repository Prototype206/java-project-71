package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

    private Parser() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String content, String format) throws Exception {
        try {
            return switch (format.toLowerCase()) {
                case "json" -> jsonMapper.readValue(content, Map.class);
                case "yml", "yaml" -> yamlMapper.readValue(content, Map.class);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            };
        } catch (Exception e) {
            throw new Exception("Failed to parse content", e);
        }
    }
}