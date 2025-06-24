package com.browser.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabManager {
    private TabPane tabPane;

    public TabManager() {
        tabPane = new TabPane();
    }

    public void addTab(String title, String url) {
        Tab newTab = new Tab(title);
        // Here you would typically add a WebView or similar component to the tab
        // For example: newTab.setContent(new WebView());
        tabPane.getTabs().add(newTab);
        loadUrlInTab(newTab, url);
    }

    public void closeTab(Tab tab) {
        tabPane.getTabs().remove(tab);
    }

    public void switchToTab(Tab tab) {
        tabPane.getSelectionModel().select(tab);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    private void loadUrlInTab(Tab tab, String url) {
        // Logic to load the URL in the WebView associated with the tab
        // This is a placeholder for actual implementation
    }
}