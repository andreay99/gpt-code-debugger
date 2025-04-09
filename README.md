GPT Code Debugger

GPT Code Debugger is a Java-based debugging assistant that uses OpenAI’s GPT models to analyze and provide suggestions for code snippets. The application features both a JavaFX GUI and a CLI, making it flexible for various development workflows.

⸻

✦ Features
	•	Automated debugging for pasted code snippets
	•	GPT-3.5 Turbo integration via OpenAI API
	•	JavaFX graphical interface for user-friendly interaction
	•	Command-line interface for quick testing or scripting

⸻

✦ Requirements
	•	Java Development Kit (JDK) — recommended: Java 21
	•	JavaFX libraries
	•	Internet connection (to access the OpenAI API)
	•	Environment variable OPENAI_API_KEY set with your OpenAI API key

⸻

✦ Setup Instructions
	1.	Clone the repository
```
git clone https://github.com/andreay99/gpt-code-debugger.git
cd gpt-code-debugger/gpt-code-debugger

```
  2.	Set your OpenAI API key
  On Unix/macOS:
```
export OPENAI_API_KEY=your_openai_api_key_here
```
On Windows (CMD):
```
set OPENAI_API_KEY=your_openai_api_key_here
```
  3.	Open the project in VS Code
	•	Install the Java and JavaFX extensions if prompted
	•	If you get a warning about JavaSE-1.8, update your settings:

"java.project.sourceVersion": "21"
Or add runtime config via "java.configuration.runtimes"

⸻ 

✦ How to Run

GUI Version

1.	Open MainGUI.java
 
2.	Run the file to launch the interface
 
3.	Paste your code into the input field and click Debug Code
    
5.	View suggestions in the output area

CLI Version

Run Main.java from the terminal or your IDE.

Currently, it will suggest using the GUI, but you can extend it with custom input/output functionality.

⸻

✦ File Overview

 •	MainGUI.java – JavaFX GUI for entering code and showing results
 
 •	Main.java – Handles OpenAI API requests and processes responses
 
 •	DebuggerUtility.java – Currently a placeholder; connect it to Main.getDebugSuggestions() to activate real debugging

⸻

✦ Example Use Case

Input
You paste this buggy JavaScript:
```
function processData(data) {
 let results = [];
 let cache = {};

 for (let i = 0; i <= data.length; i++) {
  let value = data[i];

  if (cache.value) {
   results.push(cache.value);
  } else {
   let processed = heavyComputation(value);
   cache[value] = processed;
   results.push(processed);
  }
 }
 return results;
}

function heavyComputation(input) {
 if (input < 2) return input;
 return heavyComputation(input - 1) + heavyComputation(input - 2);
}

let testInput = [5, 7, 5, 10, 7, 8];
console.log(“Processing started”);
let output = processData(testInput);
console.log(“Result:”, output);
```
⸻

Debugger Output

The tool identifies and fixes:

 •	The loop condition (from <= to <)
 
 •	The incorrect cache check (cache.value → cache[value])
 
 •	Correct reuse of processed values via consistent cache logic

⸻

Corrected Code (after debugging)
```
function processData(data) {
 let results = [];
 let cache = {};

 for (let i = 0; i < data.length; i++) {
  let value = data[i];

  if (cache[value]) {
   results.push(cache[value]);
  } else {
   let processed = heavyComputation(value);
   cache[value] = processed;
   results.push(processed);
  }
 }
 return results;
}

function heavyComputation(input) {
 if (input < 2) return input;
 return heavyComputation(input - 1) + heavyComputation(input - 2);
}

let testInput = [5, 7, 5, 10, 7, 8];
console.log(“Processing started”);
let output = processData(testInput);
console.log(“Result:”, output);
```
⸻

✦ Future Improvements
	•	Add deeper language-specific parsing and suggestions
	•	Enhance CLI features (e.g., file input, result export)
	•	Allow customization of GPT prompt behavior or verbosity
