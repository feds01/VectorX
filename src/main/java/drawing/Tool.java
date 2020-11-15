package drawing;

import java.awt.Cursor;

public class Tool {
    private final ToolType type;

    private final Cursor cursor;

    private final String resourceUri;

    public Tool(ToolType type, Cursor cursor, String resourceUri) {
        this.type = type;
        this.cursor = cursor;
        this.resourceUri = resourceUri;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public ToolType getType() {
        return type;
    }

    public String getResource() {
        return resourceUri;
    }
}
