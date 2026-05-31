package com.mycompany.datastructureproject.tests;

import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.core.models.TextEditorStatus;
import com.mycompany.datastructureproject.infrastructure.implementations.TextEditorService;

public class TextEditorServiceTest {

    private static int failures;

    public static boolean runTests() {
        failures = 0;
        testInitialState();
        testType();
        testUndo();
        testRedo();
        testRedoClearedOnNewType();
        testUndoWhenEmpty();
        testRedoWhenEmpty();
        testGetStatus();
        return failures == 0;
    }

    private static void check(boolean condition, String name) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + name);
        }
    }

    private static void testInitialState() {
        TextEditorService editor = new TextEditorService();
        check(!editor.canUndo(), "initialCannotUndo");
        check(!editor.canRedo(), "initialCannotRedo");
        check("".equals(editor.getStatus().getData().getCurrentText()), "initialEmptyText");
    }

    private static void testType() {
        TextEditorService editor = new TextEditorService();
        Result<Void> result = editor.type("Hello");
        check(result.isSuccess(), "typeSuccess");
        check("Hello".equals(editor.getStatus().getData().getCurrentText()), "typedText");
        check(editor.canUndo(), "canUndoAfterType");
    }

    private static void testUndo() {
        TextEditorService editor = new TextEditorService();
        editor.type("Hello");
        Result<Void> result = editor.undo();
        check(result.isSuccess(), "undoSuccess");
        check("".equals(editor.getStatus().getData().getCurrentText()), "textAfterUndo");
        check(editor.canRedo(), "canRedoAfterUndo");
    }

    private static void testRedo() {
        TextEditorService editor = new TextEditorService();
        editor.type("Hello");
        editor.undo();
        Result<Void> result = editor.redo();
        check(result.isSuccess(), "redoSuccess");
        check("Hello".equals(editor.getStatus().getData().getCurrentText()), "textAfterRedo");
    }

    private static void testRedoClearedOnNewType() {
        TextEditorService editor = new TextEditorService();
        editor.type("Hi");
        editor.undo();
        editor.type("!");
        check(!editor.canRedo(), "redoCleared");
        check("!".equals(editor.getStatus().getData().getCurrentText()), "newTextAfterUndoType");
    }

    private static void testUndoWhenEmpty() {
        TextEditorService editor = new TextEditorService();
        Result<Void> result = editor.undo();
        check(!result.isSuccess(), "undoEmptyFails");
        check("Nothing to undo".equals(result.getComment()), "undoEmptyMessage");
    }

    private static void testRedoWhenEmpty() {
        TextEditorService editor = new TextEditorService();
        Result<Void> result = editor.redo();
        check(!result.isSuccess(), "redoEmptyFails");
        check("Nothing to redo".equals(result.getComment()), "redoEmptyMessage");
    }

    private static void testGetStatus() {
        TextEditorService editor = new TextEditorService();
        editor.type("A");
        editor.type("B");
        TextEditorStatus status = editor.getStatus().getData();
        check(status.getUndoHistory().size() == 2, "undoHistorySize");
        check("AB".equals(status.getCurrentText()), "statusCurrentText");
        check(status.getRedoHistory().isEmpty(), "emptyRedoHistory");
    }
}
