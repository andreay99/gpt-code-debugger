# GPT Code Debugger

This project is a code debugger that uses OpenAI's GPT model to analyze and provide suggestions for broken code snippets.

## Features
- Uses OpenAI API to debug code
- Provides suggestions for fixing common syntax errors

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/andreay99/gpt-code-debugger.git

2. Navigate to the Project Directory:
cd gpt-code-debugger

3. Set Up the OpenAI API Key:

export OPENAI_API_KEY="your_api_key_here"

4. Compile the Project:

Use Maven to clean and compile the project:
mvn clean compile

5. Run the Project:

Run the application using Maven:

mvn exec:java -Dexec.mainClass="com.example.Main"

Project Structure:
gpt-code-debugger/
├── .vscode/             # VS Code settings
├── lib/                 # Library dependencies (e.g., okhttp)
├── src/                 # Source code directory
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/Main.java  # Main Java file
├── pom.xml              # Maven project configuration file
├── README.md            # Project documentation (this file)

License:

This project is licensed under the MIT License.