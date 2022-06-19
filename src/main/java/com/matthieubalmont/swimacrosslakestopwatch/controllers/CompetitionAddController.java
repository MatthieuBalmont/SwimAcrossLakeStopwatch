package com.matthieubalmont.swimacrosslakestopwatch.controllers;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

@Component
public class CompetitionAddController {

    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker dateDataPicker;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    @Lazy
    private StageManager stageManager;


    public void initialize(){
        nameTextField.setText("");
        dateDataPicker.setValue(null);

        this.dateDataPicker.setConverter(new StringConverter<LocalDate>()
        {
            private final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }

    @FXML
    private void handleCancel(Event event) {
        stageManager.switchScene(FxmlView.COMPETITION);
        event.consume();
    }

    @FXML
    private void handleAddCompetition(Event event) throws Exception {
        LocalDate localDateObj = this.dateDataPicker.getValue();
        if (localDateObj == null) {
            stageManager.showMsg("Error", "Date must not be null", Alert.AlertType.ERROR);
            return;
        }
        GregorianCalendar gc = GregorianCalendar.from(localDateObj.atStartOfDay(ZoneId.systemDefault()));
        Competition competition = new Competition(this.nameTextField.getText(), gc);
        try {
            competitionService.create(competition);
        } catch (Exception e) {
            stageManager.showMsg("Error", e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        stageManager.switchScene(FxmlView.COMPETITION);
        event.consume();
    }
}
