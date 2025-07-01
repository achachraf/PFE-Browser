package com.browser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;
import com.browser.ui.BrowserWindow;

public class BrowserApp extends Application {
    private TabPane tabPane;
    private Tab plusTab;

    @Override
    public void start(Stage stage) {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);

        plusTab = new Tab("+");
        plusTab.setClosable(false);
        tabPane.getTabs().add(plusTab);

        // create initial tab
        addNewTab();

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == plusTab) {
                addNewTab();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        stage.setTitle("Java Web Browser");
        stage.setScene(scene);
        stage.show();
    }

    private void addNewTab() {
        BrowserWindow browserWindow = new BrowserWindow();
        Tab tab = new Tab("New Tab");
        tab.setContent(browserWindow.getRoot());
        tab.setClosable(true);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tab);

        browserWindow.showHome();

        browserWindow.getWebEngine().titleProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null && !newTitle.isBlank()) {
                tab.setText(newTitle);
            } else {
                tab.setText("New Tab");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}