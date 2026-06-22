package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffNode;
import java.util.List;

public class JsonFormatter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String format(List<DiffNode> diff) throws Exception {
        return mapper.writeValueAsString(diff);
    }
}