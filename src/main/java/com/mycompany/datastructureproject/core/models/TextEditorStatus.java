package com.mycompany.datastructureproject.core.models;

import java.util.List;

public class TextEditorStatus {
    private List<String> undoHistory;
    private List<String> redoHistory;
    private String currentText;

    public TextEditorStatus(List<String> undoHistory,
                            List<String> redoHistory,
                            String currentText)
    {
        this.undoHistory = undoHistory;
        this.redoHistory = redoHistory;
        this.currentText = currentText;
    }

    public List<String> getUndoHistory() {
        return this.undoHistory;
    }

    public List<String> getRedoHistory() {
        return this.redoHistory;
    }

    public String getCurrentText() {
        return this.currentText;
    }
}
