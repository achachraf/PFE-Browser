package com.browser.ui;

import javafx.scene.control.TextField;

public class AddressBar extends TextField {

    public AddressBar() {
        super("https://example.com");
        setOnAction(e -> handleUrlSubmission());
    }

    private void handleUrlSubmission() {
        String url = getText().trim();
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        // Here you would typically notify the controller to load the URL
        // For example: controller.loadPage(url);
    }
}