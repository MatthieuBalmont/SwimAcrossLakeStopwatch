package com.matthieubalmont.swimacrosslakestopwatch.views;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.controllers.CompetitionListCellController;
import com.matthieubalmont.swimacrosslakestopwatch.controllers.RaceListCellController;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RaceListCellView extends ListCell<Race> {

    private final Parent root;
    private final RaceListCellController controller ;

    @Autowired
    public RaceListCellView(ApplicationContext context) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlView.RACE_LIST_CELL.getFxmlFile()));
            loader.setControllerFactory(context::getBean);

            this.root = loader.load();
            this.controller = loader.getController() ;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(Race race, boolean empty) {
        super.updateItem(race, empty);
        if (empty || race==null) {
            setGraphic(null);
        } else {
            this.controller.setRace(race);
            setGraphic(root);
        }
    }
}
