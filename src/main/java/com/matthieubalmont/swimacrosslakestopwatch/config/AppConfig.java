package com.matthieubalmont.swimacrosslakestopwatch.config;

import com.matthieubalmont.swimacrosslakestopwatch.utils.SpringFXMLLoader;
import com.matthieubalmont.swimacrosslakestopwatch.utils.StageManager;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:matthieu.balmont@hotmail.fr">Matthieu Balmont</a>
 */
@Configuration
public class AppConfig {
    @Autowired
    SpringFXMLLoader springFXMLLoader;

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }

}
