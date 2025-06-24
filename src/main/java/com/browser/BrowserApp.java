package com.browser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.browser.ui.BrowserWindow;

public class BrowserApp extends Application {

    @Override
    public void start(Stage stage) {
        BrowserWindow browserWindow = new BrowserWindow();
        BorderPane root = new BorderPane();
        root.setCenter(browserWindow.getRoot());

        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("Java Web Browser");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}