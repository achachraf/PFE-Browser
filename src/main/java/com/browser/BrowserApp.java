package com.browser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;
import com.browser.ui.BrowserWindow;

public class BrowserApp extends Application {

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);

        // create initial tab
        addNewTab(tabPane);

        JFXButton newTabButton = new JFXButton("+");
        newTabButton.getStyleClass().add("button-raised");
        newTabButton.setOnAction(e -> addNewTab(tabPane));

        HBox topBar = new HBox(newTabButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("Java Web Browser");
        stage.setScene(scene);
        stage.show();
    }

    private void addNewTab(TabPane tabPane) {
        BrowserWindow browserWindow = new BrowserWindow();
        Tab tab = new Tab("Tab " + (tabPane.getTabs().size() + 1));
        tab.setContent(browserWindow.getRoot());
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public static void main(String[] args) {
        launch(args);
    }
}