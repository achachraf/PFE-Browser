# Java Web Browser

## Overview
This project is a simple web browser application built using JavaFX. It provides a user-friendly interface for browsing the web, complete with features such as a URL bar, navigation buttons, and support for video playback. The application also includes caching for improved performance, error handling for various scenarios, and history management for easy navigation through previously visited pages.

## Features
- **JavaFX User Interface**: A responsive and intuitive UI built with JavaFX.
- **URL Bar**: Allows users to enter and navigate to web addresses.
- **Navigation Buttons**: Back, forward, refresh, and home buttons for easy navigation.
- **WebView Component**: Renders web pages and supports video playback.
- **Caching**: Stores resources to improve loading times and reduce network usage.
- **Error Handling**: User-friendly notifications for errors such as 404 and SSL issues.
- **History Management**: Keeps track of visited URLs for easy back and forward navigation.

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
│   │   │           │   ├── BrowserController.java
│   │   │           │   ├── HistoryController.java
│   │   │           │   └── NavigationController.java
│   │   │           ├── model
│   │   │           │   ├── BrowserHistory.java
│   │   │           │   ├── Bookmark.java
│   │   │           │   └── WebPage.java
│   │   │           ├── network
│   │   │           │   ├── CacheManager.java
│   │   │           │   ├── HttpClient.java
│   │   │           │   └── RequestHandler.java
│   │   │           ├── ui
│   │   │           │   ├── AddressBar.java
│   │   │           │   ├── BrowserWindow.java
│   │   │           │   ├── NavigationToolbar.java
│   │   │           │   └── TabManager.java
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
│                   ├── BrowserAppTest.java
│                   ├── network
│                   │   └── HttpClientTest.java
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
- The application supports video playback and will cache resources for faster loading times.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.