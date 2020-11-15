package ui.controllers;

import drawing.tool.DrawingTool;

public class ToolController {
    private DrawingTool currentTool;

    public ToolController(DrawingTool tool) {
        this.currentTool = tool;
    }

    public DrawingTool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(DrawingTool currentTool) {
        this.currentTool = currentTool;
    }
}
