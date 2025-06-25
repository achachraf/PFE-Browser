package com.browser.ui;

// import com.browser.controller.BrowserController;
import com.browser.model.BrowserHistory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class BrowserWindow {
    private BorderPane root;
    private TextField addressBar;
    private JFXButton backButton;
    private JFXButton forwardButton;
    private JFXButton refreshButton;
    private WebView webView;
    private WebEngine webEngine;
    // private BrowserController controller;
    private BrowserHistory history;

    public BrowserWindow() {
        initializeUI();
        // this.controller = new BrowserController(this);
        this.history = new BrowserHistory();
    }

    private void initializeUI() {
        root = new BorderPane();
        
        // Navigation toolbar (top)
        HBox navigationBar = new HBox(10);
        navigationBar.setPadding(new Insets(10));
        
        backButton = new JFXButton("Back");
        forwardButton = new JFXButton("Forward");
        refreshButton = new JFXButton("Refresh");
        addressBar = new TextField();
        JFXButton goButton = new JFXButton("Go");

        // Apply Material Design styling
        backButton.getStyleClass().addAll("jfx-button", "button-raised");
        forwardButton.getStyleClass().addAll("jfx-button", "button-raised");
        refreshButton.getStyleClass().addAll("jfx-button", "button-raised");
        goButton.getStyleClass().addAll("jfx-button", "button-raised");
        
        // Set button actions
        backButton.setOnAction(e -> navigateBack());
        forwardButton.setOnAction(e -> navigateForward());
        refreshButton.setOnAction(e -> refreshPage());
        goButton.setOnAction(e -> loadUrl());
        addressBar.setOnAction(e -> loadUrl());
        
        // Set HBox properties for address bar
        HBox.setHgrow(addressBar, javafx.scene.layout.Priority.ALWAYS);
        
        // Add all controls to navigation bar
        navigationBar.getChildren().addAll(
            backButton, forwardButton, refreshButton, addressBar, goButton
        );
        
        // Create WebView for page rendering
        webView = new WebView();
        webEngine = webView.getEngine();
        
        // Set up the layout
        root.setTop(navigationBar);
        root.setCenter(webView);
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
    public void displayPage(String html) {
        webEngine.loadContent(html);
    }
    
    public void loadPage(String url) {
        webEngine.load(url);
        addressBar.setText(url);
    }
    
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Browser Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void loadUrl() {
        String url = addressBar.getText();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
            addressBar.setText(url);
        }
        loadPage(url);
        history.addEntry(url);
    }
    
    private void navigateBack() {
        if (history.canGoBack()) {
            String previousUrl = history.goBack();
            if (previousUrl != null) {
                loadPage(previousUrl);
            }
        }
    }
    
    private void navigateForward() {
        if (history.canGoForward()) {
            String nextUrl = history.goForward();
            if (nextUrl != null) {
                loadPage(nextUrl);
            }
        }
    }
    
    private void refreshPage() {
        webEngine.reload();
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }
}
