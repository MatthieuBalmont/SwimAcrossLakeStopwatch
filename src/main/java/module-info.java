module SwimAcrossLakeStopwatch {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jakarta.validation;
    requires org.apache.logging.log4j;
    requires java.naming;
    requires java.sql;
    requires spring.aop;
    requires org.hibernate.validator;




    opens com.matthieubalmont.swimacrosslakestopwatch to spring.core;
    opens com.matthieubalmont.swimacrosslakestopwatch.config to spring.core;
    opens com.matthieubalmont.swimacrosslakestopwatch.views to spring.core;
    opens com.matthieubalmont.swimacrosslakestopwatch.services to spring.core;
    opens com.matthieubalmont.swimacrosslakestopwatch.utils to spring.core;
    opens com.matthieubalmont.swimacrosslakestopwatch.controllers to spring.core, javafx.fxml;
    opens com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities to org.hibernate.orm.core, org.hibernate.validator;


    exports com.matthieubalmont.swimacrosslakestopwatch.services to spring.beans;
    exports com.matthieubalmont.swimacrosslakestopwatch.views to spring.beans;
    exports com.matthieubalmont.swimacrosslakestopwatch.config to spring.beans, spring.context;
    exports com.matthieubalmont.swimacrosslakestopwatch.controllers to spring.beans;
    exports com.matthieubalmont.swimacrosslakestopwatch.utils;
    exports com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities;
    exports com.matthieubalmont.swimacrosslakestopwatch;

}