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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.List;

public class BrowserWindow {
    private BorderPane root;
    private TextField addressBar;
    private JFXButton backButton;
    private JFXButton forwardButton;
    private JFXButton refreshButton;
    private JFXButton bookmarkButton;
    private WebView webView;
    private WebEngine webEngine;
    private BrowserHistory history;
    private BookmarkManager bookmarkManager;
    private HBox bookmarksBar;
    private static final String HOME_RESOURCE = "/home.html";
    private Image bookmarkOutlineIcon;
    private Image bookmarkFilledIcon;

    public BrowserWindow() {
        this.history = new BrowserHistory();
        this.bookmarkManager = new BookmarkManager();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        
        // Load bookmark icons
        bookmarkOutlineIcon = new Image(getClass().getResourceAsStream("/icons/favorite_outline.png"));
        bookmarkFilledIcon = new Image(getClass().getResourceAsStream("/icons/favorite.png"));

        // Apply CSS stylesheet
        root.getStylesheets().add(getClass().getResource("/css/browser.css").toExternalForm());

        // Navigation toolbar (top)
        HBox navigationBar = new HBox(10);
        navigationBar.setPadding(new Insets(10));
        navigationBar.getStyleClass().add("navbar");

        bookmarksBar = new HBox(5);
        bookmarksBar.setPadding(new Insets(5,10,5,10));
        
        // Create buttons with icon-only style
        backButton = new JFXButton();
        forwardButton = new JFXButton();
        refreshButton = new JFXButton();
        bookmarkButton = new JFXButton();
        addressBar = new TextField();

        // Apply our custom styling classes
        addressBar.getStyleClass().add("url-bar");
        backButton.getStyleClass().add("nav-icon-button");
        forwardButton.getStyleClass().add("nav-icon-button");
        refreshButton.getStyleClass().add("nav-icon-button");
        bookmarkButton.getStyleClass().add("nav-icon-button");

        backButton.setGraphic(createIcon("/icons/back.png"));
        forwardButton.setGraphic(createIcon("/icons/forward.png"));
        refreshButton.setGraphic(createIcon("/icons/refresh.png"));
        bookmarkButton.setGraphic(createIconFromImage(bookmarkOutlineIcon));

        // Set button actions
        backButton.setOnAction(e -> navigateBack());
        forwardButton.setOnAction(e -> navigateForward());
        refreshButton.setOnAction(e -> refreshPage());
        bookmarkButton.setOnAction(e -> toggleCurrentPageBookmark());
        addressBar.setOnAction(e -> loadUrl());

        // Set HBox properties for address bar
        HBox.setHgrow(addressBar, javafx.scene.layout.Priority.ALWAYS);
        
        // Add all controls to navigation bar
        navigationBar.getChildren().addAll(
            backButton, forwardButton, refreshButton, addressBar, bookmarkButton
        );
        
        // Create WebView for page rendering
        webView = new WebView();
        webEngine = webView.getEngine();
        webView.getStyleClass().add("webview");

        // Update bookmark button when URL changes
        webEngine.locationProperty().addListener((obs, oldLoc, newLoc) -> {
            if (newLoc != null) {
                updateBookmarkButtonState(newLoc);
            }
        });

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

    public void showHome() {
        loadHome();
        addressBar.clear();
    }

    private void loadHome() {
        URL url = getClass().getResource(HOME_RESOURCE);
        if (url != null) {
            webEngine.load(url.toExternalForm());
        }
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
        updateBookmarkButtonState(url);
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

    private ImageView createIcon(String path) {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream(path)));
        view.setFitWidth(18);
        view.setFitHeight(18);
        return view;
    }

    private ImageView createIconFromImage(Image image) {
        ImageView view = new ImageView(image);
        view.setFitWidth(18);
        view.setFitHeight(18);
        return view;
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

    private void toggleCurrentPageBookmark() {
        String currentUrl = webEngine.getLocation();
        String pageTitle = webEngine.getTitle();

        if (currentUrl == null || currentUrl.isEmpty()) {
            return;
        }

        // Check if the current URL is already bookmarked
        boolean isBookmarked = bookmarkManager.isUrlBookmarked(currentUrl);

        if (isBookmarked) {
            // Remove the bookmark
            removeBookmark(currentUrl);
            bookmarkButton.setGraphic(createIconFromImage(bookmarkOutlineIcon));
        } else {
            // Add the bookmark
            if (pageTitle == null || pageTitle.isEmpty()) {
                pageTitle = currentUrl;
            }
            bookmarkManager.addBookmark(pageTitle, currentUrl);
            bookmarkButton.setGraphic(createIconFromImage(bookmarkFilledIcon));
            updateBookmarksBar();
        }
    }

    private void removeBookmark(String url) {
        List<Bookmark> bookmarks = bookmarkManager.getBookmarks();
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getUrl().equals(url)) {
                bookmarkManager.removeBookmark(i);
                updateBookmarksBar();
                break;
            }
        }
    }

    private void updateBookmarkButtonState(String url) {
        if (bookmarkManager.isUrlBookmarked(url)) {
            bookmarkButton.setGraphic(createIconFromImage(bookmarkFilledIcon));
        } else {
            bookmarkButton.setGraphic(createIconFromImage(bookmarkOutlineIcon));
        }
    }
}
