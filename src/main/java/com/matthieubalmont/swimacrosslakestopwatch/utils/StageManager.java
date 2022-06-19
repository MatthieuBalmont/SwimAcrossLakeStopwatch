package com.matthieubalmont.swimacrosslakestopwatch.utils;


import java.util.Objects;
import java.util.Optional;

import com.matthieubalmont.swimacrosslakestopwatch.config.FxmlView;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionServiceImpl;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class StageManager {
    @Autowired
    private Environment env;
    private static final Logger logger = LogManager.getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy);
    }

    private void show(final Parent rootnode) {
        Scene scene = prepareScene(rootnode);

        primaryStage.setTitle(env.getProperty("spring.application.ui.title"));
        primaryStage.setScene(scene);
        //primaryStage.sizeToScene();
        //primaryStage.centerOnScreen();
        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene",  exception);
        }
    }

    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode, primaryStage.getWidth(),primaryStage.getHeight());
        }
        scene.setRoot(rootnode);
        return scene;
    }

    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view " + fxmlFilePath, exception);
        }
        return rootNode;
    }

    private void logAndExit(String errorMsg, Exception exception) {
        logger.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public boolean showMsg(String title, String content, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(content);


        Optional<ButtonType> result = errorAlert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        }
        return true;
    }

}
