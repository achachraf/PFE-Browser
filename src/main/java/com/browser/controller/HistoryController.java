package com.browser.controller;

import com.browser.model.BrowserHistory;
import java.util.List;

public class HistoryController {
    private BrowserHistory history;

    public HistoryController() {
        this.history = new BrowserHistory();
    }

    public void addToHistory(String url) {
        history.addEntry(url);
    }

    public String goBack() {
        return history.goBack();
    }

    public String goForward() {
        return history.goForward();
    }

    public void clearHistory() {
        // We'll implement this method in BrowserHistory
        // For now, we'll just create a new history object
        this.history = new BrowserHistory();
    }

    public List<String> getHistoryEntries() {
        return history.getHistory();
    }
}