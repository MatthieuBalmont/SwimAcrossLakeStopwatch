package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import com.matthieubalmont.swimacrosslakestopwatch.services.RaceService;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import com.matthieubalmont.swimacrosslakestopwatch.views.CompetitionListCellView;
import com.matthieubalmont.swimacrosslakestopwatch.views.RaceListCellView;
import javafx.event.ActionEvent;
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
public class RaceController implements Initializable {

    private final ApplicationContext context;
    @FXML
    private ListView<Race> listViewRace;
    @Autowired
    private RaceService raceService;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    @Lazy
    private StageManager stageManager;
    List<Race> races;

    public RaceController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            this.races = this.raceService.findAllByCompetition(this.competitionService.getCurrentCompetition());
        } catch (Exception e) {
            stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
            this.races = new ArrayList<>();
        }
        this.listViewRace.getItems().addAll(this.races);
        this.listViewRace.setCellFactory(lv -> new RaceListCellView(context));
    }

    @FXML
    public void handleAddRace(ActionEvent actionEvent) {
        actionEvent.consume();
        stageManager.switchScene(FxmlView.ADD_RACE);
    }

    public void handleGoBack(ActionEvent actionEvent) {
        actionEvent.consume();
        stageManager.switchScene(FxmlView.TIMING);
    }
}
