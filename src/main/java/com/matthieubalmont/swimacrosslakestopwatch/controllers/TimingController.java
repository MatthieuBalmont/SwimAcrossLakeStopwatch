package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TimingController {

    public Label timerLabel;

    public void handleStartWaveButton(ActionEvent actionEvent) {
    }
}
