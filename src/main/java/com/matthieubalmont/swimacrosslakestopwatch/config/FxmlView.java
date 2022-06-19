package com.matthieubalmont.swimacrosslakestopwatch.config;

/**
 * @author <a href="mailto:matthieu.balmont@hotmail.fr">Matthieu Balmont</a>
 */
public enum FxmlView {

    COMPETITION {
        @Override
        public String getFxmlFile() {
            return "/fxml/CompetitionView.fxml";
        }
    },
    ADD_COMPETITION {
        @Override
        public String getFxmlFile() {
            return "/fxml/CompetitionAddView.fxml";
        }
    },
    COMPETITION_LIST_CELL{
        @Override
        public String getFxmlFile() {
            return "/fxml/CompetitionListCellView.fxml";
        }
    };

    public abstract String getFxmlFile();
}
