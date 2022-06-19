package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.utils.HibernateUtils;
import jakarta.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static final Logger logger = LogManager.getLogger(CompetitionServiceImpl.class);
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public List<Competition> findAll() throws Exception {
        List<Competition> competitions;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            competitions = session.createQuery("select c from Competition c", Competition.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.warn("Exception raised in findAll : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
        return competitions;
    }

    public Competition find(String id) throws Exception {
        Competition competition;
        Transaction transaction = null;


        if(id == null){
            logger.warn("Find data problem : id must not be 'null'");
            throw new Exception("Id must not be 'null'");
        }
        else {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                competition = session.find(Competition.class, id);
                transaction.commit();
            } catch (Exception e) {
                logger.warn("Exception raised in find : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
        if (competition == null){
            logger.warn("Competition (" + id + ") didn't find");
            throw new Exception("Competition didn't find");
        }
        return competition;
    }

    public void create(Competition competition) throws Exception {
        Transaction transaction = null;
        checkDataConformity(competition, "Create");

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(competition);
            transaction.commit();
            logger.info("Competition (" + competition.getId() + ") added to data base");
        } catch (Exception e) {
            logger.warn("Exception raised in create : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
    }

    public void delete(Competition competition) throws Exception {
        Transaction transaction = null;

        checkDataConformity(competition, "Delete");

        if(this.existById(competition.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(competition);
                transaction.commit();
                logger.info("Competition (" + competition.getId() + ") deleted from data base");
            } catch (Exception e) {
                logger.warn("Exception raised in delete : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
    }

    public void update(Competition competition) throws Exception {
        Transaction transaction = null;

        checkDataConformity(competition, "Update");

        if (this.existById(competition.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.update(competition);
                transaction.commit();
                logger.info("Competition (" + competition.getId() + ") updated from data base");
            } catch (Exception e) {
                logger.warn("Exception raised in update : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
    }

    public boolean existById(String id) throws Exception {
        Competition competition = this.find(id);
        return competition != null;
    }

    private void checkDataConformity(Competition competition, String methodName) throws Exception {
        if(competition == null){
            logger.warn(methodName + " data problem : competition must not be 'null'");
            throw new Exception("Competition must not be 'null'");
        }
        //TODO make a string to see all errors
        Set<ConstraintViolation<Competition>> constraintViolations = this.validator.validate(competition);
        for (ConstraintViolation<Competition> c : constraintViolations) {
            logger.warn(methodName + " data problem : " + c.getMessage());
            throw new Exception(c.getMessage());
        }
    }
}
