package com.matthieubalmont.swimacrosslakestopwatch.views;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.controllers.CompetitionListCellController;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CompetitionListCellView extends ListCell<Competition> {

    private final Parent root;
    private final CompetitionListCellController controller ;

    @Autowired
    public CompetitionListCellView(ApplicationContext context) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlView.COMPETITION_LIST_CELL.getFxmlFile()));
            loader.setControllerFactory(context::getBean);

            this.root = loader.load();
            this.controller = loader.getController() ;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(Competition competition, boolean empty) {
        super.updateItem(competition, empty);
        if (empty || competition==null) {
            setGraphic(null);
        } else {
            this.controller.setCompetition(competition);
            setGraphic(root);
        }
    }
}
