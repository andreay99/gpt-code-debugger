package com.example.Controller;

import com.example.model.DebuggerModel;

public class MainController {
    private DebuggerModel model = new DebuggerModel();

    /**
 * I handle the debug action here: first, I check if the code snippet is valid,
 * then I pass it over to my model to get the debugging result.
 *
 * @param codeSnippet the code snippet from the view that needs debugging
 * @return the debugging result as a string
 */
    public String handleDebugAction(String codeSnippet) {
        if (codeSnippet == null || codeSnippet.trim().isEmpty()) {
            return "Please enter a code snippet to debug.";
        }
        // Delegate the debugging logic to the model.
        return model.debugCode(codeSnippet.trim());
    }
}
