package com.browser.model;

import java.util.ArrayList;
import java.util.List;

public class BrowserHistory {
    private List<String> history;
    private int currentIndex;

    public BrowserHistory() {
        history = new ArrayList<>();
        currentIndex = -1;
    }

    public void addEntry(String url) {
        // If we're not at the end of the history, remove all entries after current index
        if (currentIndex < history.size() - 1) {
            history = new ArrayList<>(history.subList(0, currentIndex + 1));
        }
        
        history.add(url);
        currentIndex = history.size() - 1;
    }

    public boolean canGoBack() {
        return currentIndex > 0;
    }

    public boolean canGoForward() {
        return currentIndex < history.size() - 1;
    }

    public String goBack() {
        if (canGoBack()) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null;
    }

    public String goForward() {
        if (canGoForward()) {
            currentIndex++;
            return history.get(currentIndex);
        }
        return null;
    }

    public String getCurrentUrl() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            return history.get(currentIndex);
        }
        return null;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
