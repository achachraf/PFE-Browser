package com.browser.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages bookmarks persistence and visibility.
 */
public class BookmarkManager {
    private final Path filePath;
    private final List<Bookmark> bookmarks = new ArrayList<>();
    private boolean barVisible = true;

    public BookmarkManager() {
        Path dir = Paths.get(System.getProperty("user.home"), ".h2-browser", "cache");
        this.filePath = dir.resolve("bookmarks.json");
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                // ignore
            }
        }
        load();
    }

    private void load() {
        if (!Files.exists(filePath)) {
            return;
        }
        try {
            String json = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            bookmarks.clear();
            Pattern entryPattern = Pattern.compile("\\{\\s*\"name\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"url\"\\s*:\\s*\"(.*?)\"\\s*\\}");
            Matcher matcher = entryPattern.matcher(json);
            while (matcher.find()) {
                bookmarks.add(new Bookmark(matcher.group(1), matcher.group(2)));
            }
            if (json.contains("\"visible\":false")) {
                barVisible = false;
            } else {
                barVisible = true;
            }
        } catch (IOException e) {
            // ignore
        }
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"visible\":").append(barVisible).append(",\n  \"bookmarks\":[");
        for (int i = 0; i < bookmarks.size(); i++) {
            Bookmark b = bookmarks.get(i);
            sb.append("{\"name\":\"").append(escape(b.getName())).append("\",\"url\":\"").append(escape(b.getUrl())).append("\"}");
            if (i < bookmarks.size() - 1) sb.append(',');
        }
        sb.append("]\n}");
        try {
            Files.write(filePath, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // ignore
        }
    }

    public List<Bookmark> getBookmarks() {
        return new ArrayList<>(bookmarks);
    }

    public void addBookmark(String name, String url) {
        bookmarks.add(new Bookmark(name, url));
        save();
    }

    public boolean isBarVisible() {
        return barVisible;
    }

    public void setBarVisible(boolean visible) {
        this.barVisible = visible;
        save();
    }
}
