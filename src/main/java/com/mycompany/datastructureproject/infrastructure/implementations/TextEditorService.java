package com.mycompany.datastructureproject.infrastructure.implementations;

import com.mycompany.datastructureproject.core.Interfaces.ITextEditor;
import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.core.models.TextEditorStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TextEditor implements ITextEditor {

    private Stack<String> undoStack;
    private Stack<String> redoStack;
    private String currentText;

    public TextEditor() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        currentText = "";
    }

    public Result<Void> type(String text) {
        undoStack.push(currentText);
        currentText = currentText + text;
        redoStack.clear();
        return new Result(true);
    }

    public Result<Void> undo() {
        if (canUndo()) {
            redoStack.push(currentText);
            currentText = undoStack.pop();
            return new Result(true);}
        else {
            return new Result(false, "Nothing to undo");
        } }
    public Result<Void> redo() {
        if (canRedo()) {
            undoStack.push(currentText);
            currentText = redoStack.pop();
            return new Result(true);}
        else {
            return new Result(false, "Nothing to redo");
        } }

    public Result<TextEditorStatus> getStatus() {
        List<String> undos = new ArrayList<>(undoStack);
        List<String> redos = new ArrayList<>(redoStack);
        return new Result(true, new TextEditorStatus(undos, redos, this.currentText));
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }
}
