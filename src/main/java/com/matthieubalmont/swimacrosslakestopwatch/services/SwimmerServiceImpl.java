package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Swimmer;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.utils.HibernateUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SwimmerServiceImpl implements SwimmerService {

    private final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static final Logger logger = LogManager.getLogger(SwimmerServiceImpl.class);
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public List<Swimmer> findAll() throws Exception {
        List<Swimmer> swimmers;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            swimmers = session.createQuery("select s from Swimmer s", Swimmer.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.warn("Exception raised in findAll : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
        return swimmers;
    }

    @Override
    public Swimmer find(String id) throws Exception {
        Swimmer swimmer;
        Transaction transaction = null;


        if(id == null){
            logger.warn("Find data problem : id must not be 'null'");
            throw new Exception("Id must not be 'null'");
        }
        else {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                swimmer = session.find(Swimmer.class, id);
                transaction.commit();
            } catch (Exception e) {
                logger.warn("Exception raised in find : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
        if (swimmer == null){
            logger.warn("Swimmer (" + id + ") didn't find");
            throw new Exception("Swimmer didn't find");
        }
        return swimmer;
    }

    @Override
    public void create(Swimmer swimmer) throws Exception {
        Transaction transaction = null;
        checkDataConformity(swimmer, "Create");

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(swimmer);
            transaction.commit();
            logger.info("Swimmer (" + swimmer.getId() + ") added to data base");
        } catch (Exception e) {
            logger.warn("Exception raised in create : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
    }

    @Override
    public void delete(Swimmer swimmer) throws Exception {
        Transaction transaction = null;

        checkDataConformity(swimmer, "Delete");

        if(this.existById(swimmer.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(swimmer);
                transaction.commit();
                logger.info("Swimmer (" + swimmer.getId() + ") deleted from data base");
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
    public void update(Swimmer swimmer) throws Exception {
        Transaction transaction = null;

        checkDataConformity(swimmer, "Update");

        if (this.existById(swimmer.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.update(swimmer);
                transaction.commit();
                logger.info("Swimmer (" + swimmer.getId() + ") updated to data base");
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
        Swimmer swimmer = this.find(id);
        return swimmer != null;
    }

    private void checkDataConformity(Swimmer swimmer, String methodName) throws Exception {
        if(swimmer == null){
            logger.warn(methodName + " data problem : swimmer must not be 'null'");
            throw new Exception("Swimmer must not be 'null'");
        }
        //TODO make a string to see all errors
        Set<ConstraintViolation<Swimmer>> constraintViolations = this.validator.validate(swimmer);
        for (ConstraintViolation<Swimmer> s : constraintViolations) {
            logger.warn(methodName + " data problem : " + s.getMessage());
            throw new Exception(s.getMessage());
        }
    }
}
