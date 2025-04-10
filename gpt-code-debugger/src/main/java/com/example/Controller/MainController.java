package com.example.Controller;

import com.example.model.DebuggerModel;

public class MainController {
    private DebuggerModel model = new DebuggerModel();

    /**
     * Handles the debug action by validating the code snippet and delegating to the model.
     *
     * @param codeSnippet The code snippet provided by the view.
     * @return The debugging result.
     */
    public String handleDebugAction(String codeSnippet) {
        if (codeSnippet == null || codeSnippet.trim().isEmpty()) {
            return "Please enter a code snippet to debug.";
        }
        // Delegate the debugging logic to the model.
        return model.debugCode(codeSnippet.trim());
    }
}
