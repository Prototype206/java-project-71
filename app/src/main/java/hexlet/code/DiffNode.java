package hexlet.code;

public class DiffNode {
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final DiffStatus status;

    public enum DiffStatus {
        UNCHANGED,
        ADDED,
        REMOVED,
        CHANGED
    }

    public DiffNode(String key, Object oldValue, Object newValue, DiffStatus status) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public DiffStatus getStatus() {
        return status;
    }
}