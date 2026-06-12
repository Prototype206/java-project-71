package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DifferBuilder {

    public static List<DiffNode> buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        List<DiffNode> diff = new ArrayList<>();

        for (String key : allKeys) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);

            if (!inFirst && inSecond) {
                diff.add(new DiffNode(key, null, data2.get(key), DiffNode.DiffStatus.ADDED));
            } else if (inFirst && !inSecond) {
                diff.add(new DiffNode(key, data1.get(key), null, DiffNode.DiffStatus.REMOVED));
            } else {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                if (Objects.equals(value1, value2)) {
                    diff.add(new DiffNode(key, value1, value2, DiffNode.DiffStatus.UNCHANGED));
                } else {
                    diff.add(new DiffNode(key, value1, value2, DiffNode.DiffStatus.CHANGED));
                }
            }
        }

        return diff;
    }
}