package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.utils.HibernateUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RaceServiceImpl implements RaceService{

    private final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static final Logger logger = LogManager.getLogger(RaceServiceImpl.class);
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public List<Race> findAll() throws Exception {
        List<Race> races;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            races = session.createQuery("select r from Race r", Race.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.warn("Exception raised in findAll : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
        return races;
    }

    @Override
    public List<Race> findAllByCompetition(Competition competition) throws Exception {
        List<Race> races;
        Transaction transaction = null;

        if(competition == null){
            logger.warn("findAllByCompetition data problem : competition must not be 'null'");
            throw new Exception("Competition must not be 'null'");
        }
        if(competition.getId() == null){
            logger.warn("findAllByCompetition data problem : competition id must not be 'null'");
            throw new Exception("Competition id must not be 'null'");
        }

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            races = session.createQuery("select r from Race r where r.competition = :competition order by r.startOrder", Race.class)
                    .setParameter("competition", competition)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.warn("Exception raised in findAllByCompetition : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
        return races;
    }

    @Override
    public Race find(String id) throws Exception {
        Race race;
        Transaction transaction = null;

        if(id == null){
            logger.warn("Find data problem : id must not be 'null'");
            throw new Exception("Id must not be 'null'");
        }
        else {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                race = session.find(Race.class, id);
                transaction.commit();
            } catch (Exception e) {
                logger.warn("Exception raised in find : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
        if (race == null){
            logger.warn("Race (" + id + ") didn't find");
            throw new Exception("Race didn't find");
        }
        return race;
    }

    @Override
    public void create(Race race) throws Exception {
        Transaction transaction = null;
        checkDataConformity(race, "Create");

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(race);
            transaction.commit();
            logger.info("Race (" + race.getId() + ") added to data base");
        } catch (Exception e) {
            logger.warn("Exception raised in create : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
    }

    @Override
    public void delete(Race race) throws Exception {
        Transaction transaction = null;

        checkDataConformity(race, "Delete");

        if(this.existById(race.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(race);
                transaction.commit();
                logger.info("Race (" + race.getId() + ") deleted from data base");
            } catch (Exception e) {
                logger.warn("Exception raised in delete : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
    }

    @Override
    public void update(Race race) throws Exception {
        Transaction transaction = null;

        checkDataConformity(race, "Update");

        if (this.existById(race.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.update(race);
                transaction.commit();
                logger.info("Race (" + race.getId() + ") updated to data base");
            } catch (Exception e) {
                logger.warn("Exception raised in update : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
    }

    @Override
    public boolean existById(String id) throws Exception {
        Race race = this.find(id);
        return race != null;
    }

    private void checkDataConformity(Race race, String methodName) throws Exception {
        if(race == null){
            logger.warn(methodName + " data problem : competition must not be 'null'");
            throw new Exception("Race must not be 'null'");
        }
        //TODO make a string to see all errors
        Set<ConstraintViolation<Race>> constraintViolations = this.validator.validate(race);
        for (ConstraintViolation<Race> r : constraintViolations) {
            logger.warn(methodName + " data problem : " + r.getMessage());
            throw new Exception(r.getMessage());
        }
    }
}
