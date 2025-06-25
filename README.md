# Java Web Browser

## Overview
This project is a simple web browser application built using JavaFX. It provides a user-friendly interface for browsing the web, complete with a URL bar, navigation buttons, and history management for easy navigation through previously visited pages.

## Features
- **JavaFX User Interface**: A responsive and intuitive UI built with JavaFX.
- **Material Design**: Styled using the JFoenix library for modern components.
- **URL Bar**: Allows users to enter and navigate to web addresses.
- **Navigation Buttons**: Back, forward, refresh, and home buttons for easy navigation.
- **WebView Component**: Renders web pages.
- **Error Handling**: User-friendly notifications for errors such as 404 and SSL issues.
- **History Management**: Keeps track of visited URLs for easy back and forward navigation.
- **Tabbed Browsing**: Open and close multiple tabs within the same window.

## Project Structure
```
java-web-browser
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── browser
│   │   │           ├── BrowserApp.java
│   │   │           ├── controller
│   │   │           │   ├── HistoryController.java
│   │   │           │   └── NavigationController.java
│   │   │           ├── model
│   │   │           │   └── BrowserHistory.java
│   │   │           ├── ui
│   │   │           │   └── BrowserWindow.java
│   │   │           └── util
│   │   │               ├── ErrorHandler.java
│   │   │               └── UrlValidator.java
│   │   └── resources
│   │       ├── css
│   │       │   └── browser.css
│   │       ├── icons
│   │       │   ├── back.png
│   │       │   ├── forward.png
│   │       │   ├── home.png
│   │       │   └── refresh.png
│   │       └── fxml
│   │           └── browser.fxml
│   └── test
│       └── java
│           └── com
│               └── browser
│                   └── util
│                       └── UrlValidatorTest.java
├── pom.xml
└── README.md
```

## Setup Instructions
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Ensure you have Maven installed.
4. Run `mvn clean install` to build the project.
5. Execute the application using `mvn javafx:run`.

## Usage
- Enter a URL in the address bar and press Enter or click the "Go" button to navigate.
- Use the navigation buttons to move back and forth through your browsing history.
- The application will load pages entered in the address bar.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.