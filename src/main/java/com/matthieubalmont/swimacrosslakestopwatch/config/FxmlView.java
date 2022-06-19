package com.matthieubalmont.swimacrosslakestopwatch.config;

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
