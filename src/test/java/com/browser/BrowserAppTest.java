package com.browser;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class BrowserAppTest {

    private BrowserApp browserApp;
    private Stage stage;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        this.browserApp = new BrowserApp();
    }

    @BeforeEach
    void setUp() {
        // No need to initialize browserApp here as it's done in the @Start method
    }

    @Test
    void testLaunch() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    browserApp.start(stage);
                    assertNotNull(stage.getScene());
                    assertEquals("Java Web Browser", stage.getTitle());
                } catch (Exception e) {
                    fail("Exception thrown: " + e.getMessage());
                }
            });
        });
    }
}