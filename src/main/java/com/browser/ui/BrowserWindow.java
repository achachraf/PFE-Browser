package com.browser.ui;

import com.browser.model.BrowserHistory;
import com.browser.model.Bookmark;
import com.browser.model.BookmarkManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
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
    private BrowserHistory history;
    private BookmarkManager bookmarkManager;
    private HBox bookmarksBar;

    public BrowserWindow() {
        this.history = new BrowserHistory();
        this.bookmarkManager = new BookmarkManager();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        
        // Navigation toolbar (top)
        HBox navigationBar = new HBox(10);
        navigationBar.setPadding(new Insets(10));

        bookmarksBar = new HBox(5);
        bookmarksBar.setPadding(new Insets(5,10,5,10));
        
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
        
        updateBookmarksBar();
        boolean visible = bookmarkManager.isBarVisible();
        bookmarksBar.setVisible(visible);
        bookmarksBar.setManaged(visible);

        // Set up the layout
        VBox topContainer = new VBox(navigationBar, bookmarksBar);
        root.setTop(topContainer);
        root.setCenter(webView);

        topContainer.setOnContextMenuRequested(e ->
            showContextMenu(topContainer, e.getScreenX(), e.getScreenY()));
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

    private void addBookmark() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Bookmark");
        nameDialog.setHeaderText("Bookmark Name");
        nameDialog.setContentText("Name:");
        var nameResult = nameDialog.showAndWait();
        if (nameResult.isEmpty() || nameResult.get().isBlank()) {
            return;
        }
        String defaultUrl = addressBar.getText();
        TextInputDialog urlDialog = new TextInputDialog(defaultUrl);
        urlDialog.setTitle("Add Bookmark");
        urlDialog.setHeaderText("Bookmark URL");
        urlDialog.setContentText("URL:");
        var urlResult = urlDialog.showAndWait();
        if (urlResult.isEmpty() || urlResult.get().isBlank()) {
            return;
        }
        String url = urlResult.get();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        bookmarkManager.addBookmark(nameResult.get(), url);
        updateBookmarksBar();
    }

    private void toggleBookmarks() {
        boolean visible = !bookmarksBar.isVisible();
        bookmarksBar.setVisible(visible);
        bookmarksBar.setManaged(visible);
        bookmarkManager.setBarVisible(visible);
    }

    private void updateBookmarksBar() {
        bookmarksBar.getChildren().clear();
        for (Bookmark b : bookmarkManager.getBookmarks()) {
            Button btn = new Button(b.getName());
            btn.getStyleClass().add("bookmark-button");
            btn.setOnAction(e -> loadPage(b.getUrl()));
            bookmarksBar.getChildren().add(btn);
        }
    }

    private void showContextMenu(Node parent, double x, double y) {
        ContextMenu menu = new ContextMenu();
        MenuItem toggleItem = new MenuItem(bookmarksBar.isVisible() ? "Hide Bookmarks" : "Show Bookmarks");
        toggleItem.setOnAction(e -> toggleBookmarks());
        MenuItem addItem = new MenuItem("Add Bookmark");
        addItem.setOnAction(e -> addBookmark());
        menu.getItems().addAll(toggleItem, addItem);
        menu.show(parent, x, y);
    }
}
