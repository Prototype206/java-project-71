package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String filepath) throws Exception {
        String content = Files.readString(Path.of(filepath));
        String extension = getExtension(filepath);

        return switch (extension) {
            case "json" -> jsonMapper.readValue(content, Map.class);
            case "yml", "yaml" -> yamlMapper.readValue(content, Map.class);
            default -> throw new IllegalArgumentException("Unsupported file type: " + extension);
        };
    }

    private static String getExtension(String filepath) {
        int lastDot = filepath.lastIndexOf('.');
        if (lastDot == -1) {
            throw new IllegalArgumentException("File has no extension: " + filepath);
        }
        return filepath.substring(lastDot + 1).toLowerCase();
    }
}