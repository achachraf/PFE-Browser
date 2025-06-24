package com.browser.controller;

import javafx.scene.web.WebEngine;

public class NavigationController {
    private WebEngine webEngine;

    public NavigationController(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public void navigateBack() {
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
        }
    }

    public void navigateForward() {
        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
            webEngine.getHistory().go(1);
        }
    }

    public void refreshPage() {
        webEngine.reload();
    }

    public void goHome(String homeUrl) {
        webEngine.load(homeUrl);
    }
}