package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import com.matthieubalmont.swimacrosslakestopwatch.services.RaceService;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

@Component
public class RaceAddController {
    @FXML
    public TextField distanceTextField;
    @FXML
    public TextField startOrderTextField;
    @Autowired
    private RaceService raceService;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    @Lazy
    private StageManager stageManager;


    public void initialize(){
        distanceTextField.setText("");
        startOrderTextField.setText("");
    }

    @FXML
    private void handleCancel(Event event) {
        stageManager.switchScene(FxmlView.RACE);
        event.consume();
    }

    @FXML
    public void handleAddRace(ActionEvent actionEvent) {
        actionEvent.consume();
        int distance;
        int startOrder;

        try{
            distance = Integer.parseInt(distanceTextField.getText());
        }
        catch (NumberFormatException ex){
            stageManager.showMsg("Error", "Distance must be numeric", Alert.AlertType.ERROR);
            return;
        }
        try{
            startOrder = Integer.parseInt(startOrderTextField.getText());
        }
        catch (NumberFormatException ex){
            stageManager.showMsg("Error", "Start order must be numeric", Alert.AlertType.ERROR);
            return;
        }

        Race race = new Race(distance, startOrder, competitionService.getCurrentCompetition());
        try {
            raceService.create(race);
        } catch (Exception e) {
            stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        stageManager.switchScene(FxmlView.RACE);
    }

    public void changeStartOrder(KeyEvent keyEvent) {
        keyEvent.consume();
        if (!startOrderTextField.getText().matches("\\d*")) {
            startOrderTextField.setText(startOrderTextField.getText().replaceAll("[^\\d]", ""));
        }
    }

    public void changeDistance(KeyEvent keyEvent) {
        keyEvent.consume();
        if (!distanceTextField.getText().matches("\\d*")) {
            distanceTextField.setText(distanceTextField.getText().replaceAll("[^\\d]", ""));
        }
    }
}
