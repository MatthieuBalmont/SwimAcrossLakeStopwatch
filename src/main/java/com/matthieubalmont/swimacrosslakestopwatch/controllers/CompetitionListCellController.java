package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
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
public class CompetitionListCellController {

    @FXML
    private Label titleLabel;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    @Lazy
    private StageManager stageManager;

    private Competition competition;

    public void setCompetition(Competition competition) {
        this.competition = competition;
        this.titleLabel.setText(competition.getDateToString() + " - " + competition.getName());
    }

    @FXML
    private void handleDelete(Event event) {
        event.consume();
        try {
            this.competitionService.delete(this.competition);
        } catch (Exception e) {
            this.stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        //TODO Not really clear
        stageManager.switchScene(FxmlView.COMPETITION);
    }

    @FXML
    private void handleOpen(Event event) {
        System.out.println(competition.toString() + " : " + event);
        event.consume();
    }

}
