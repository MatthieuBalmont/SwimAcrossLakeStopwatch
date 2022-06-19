package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Swimmer;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import com.matthieubalmont.swimacrosslakestopwatch.services.SwimmerService;
import com.matthieubalmont.swimacrosslakestopwatch.services.SwimmingClubService;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import com.matthieubalmont.swimacrosslakestopwatch.views.CompetitionListCellView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CompetitionController implements Initializable {

    private ApplicationContext context;
    @FXML
    private ListView<Competition> listViewCompetition;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    @Lazy
    private StageManager stageManager;
    List<Competition> competitions;

    public CompetitionController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            this.competitions = this.competitionService.findAll();
        } catch (Exception e) {
            stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
            this.competitions = new ArrayList<>();
        }
        this.listViewCompetition.getItems().addAll(this.competitions);
        this.listViewCompetition.setCellFactory(lv -> new CompetitionListCellView(context));
    }

    @FXML
    private void handleAddCompetition(Event event) {
        event.consume();
        stageManager.switchScene(FxmlView.ADD_COMPETITION);
    }
}
