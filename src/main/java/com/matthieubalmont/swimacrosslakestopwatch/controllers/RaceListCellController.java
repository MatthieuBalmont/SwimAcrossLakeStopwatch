package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import com.matthieubalmont.swimacrosslakestopwatch.services.RaceService;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RaceListCellController {
    @FXML
    private Label titleLabel;
    @Autowired
    private RaceService raceService;
    @Autowired
    @Lazy
    private StageManager stageManager;

    private Race race;

    public void setRace(Race race) {
        this.race = race;
        this.titleLabel.setText("Start order : " + race.getStartOrder() + " - " + race.getDistance() + "m");
    }

    @FXML
    private void handleDelete(Event event) {
        event.consume();
        try {
            this.raceService.delete(this.race);
        } catch (Exception e) {
            this.stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        //TODO Not really clear
        stageManager.switchScene(FxmlView.RACE);
    }

    @FXML
    private void handleOpen(Event event) {
        event.consume();
        stageManager.switchScene(FxmlView.TIMING);
    }
}
