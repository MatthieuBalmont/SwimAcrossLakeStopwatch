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
    },
    TIMING{
        @Override
        public String getFxmlFile() {
            return "/fxml/TimingView.fxml";
        }
    },
    RACE{
        @Override
        public String getFxmlFile() {
            return "/fxml/RaceView.fxml";
        }
    },
    ADD_RACE{
        @Override
        public String getFxmlFile() {
            return "/fxml/RaceAddView.fxml";
        }
    },
    RACE_LIST_CELL{
        @Override
        public String getFxmlFile() {
            return "/fxml/RaceListCellView.fxml";
        }
    };

    public abstract String getFxmlFile();
}
