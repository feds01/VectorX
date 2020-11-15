package ui.controllers;

import drawing.Tool;

public class ToolController {
    private Tool currentTool;

    public ToolController(Tool tool) {
        this.currentTool = tool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }
}
