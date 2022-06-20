package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MenuBarController {
    @Autowired
    @Lazy
    private StageManager stageManager;
    public void handleNewMenu(ActionEvent actionEvent) {
        actionEvent.consume();
        this.stageManager.switchScene(FxmlView.ADD_COMPETITION);
    }

    public void handleCloseMenu(ActionEvent actionEvent) {
        actionEvent.consume();
        this.stageManager.switchScene(FxmlView.COMPETITION);
    }

    public void handleQuitMenu(ActionEvent actionEvent) {
    }

    public void handleRaceMenu(ActionEvent actionEvent) {
        actionEvent.consume();
        this.stageManager.switchScene(FxmlView.RACE);
    }

    public void handleSwimmerMenu(ActionEvent actionEvent) {
    }

    public void handleSwimmingCulbMenu(ActionEvent actionEvent) {
    }

    public void handleAboutMenu(ActionEvent actionEvent) {
    }
}
