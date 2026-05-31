package com.mycompany.datastructureproject.core.Interfaces;
    import com.mycompany.datastructureproject.core.models.Result;
    import com.mycompany.datastructureproject.core.models.TextEditorStatus;

public interface ITextEditorService {
    Result<Void> type(String text);
    Result<Void> undo();
    Result<Void> redo();
    Result<TextEditorStatus> getStatus();
    boolean canUndo();
    boolean canRedo();
}
    

