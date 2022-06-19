package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;
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
public class SwimmingClubServiceImpl implements SwimmingClubService {
    private final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static final Logger logger = LogManager.getLogger(SwimmingClubServiceImpl.class);
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public List<SwimmingClub> findAll() throws Exception {
        List<SwimmingClub> swimmingClubs;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            swimmingClubs = session.createQuery("select sc from SwimmingClub sc", SwimmingClub.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            logger.warn("Exception raised in findAll : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
        return swimmingClubs;
    }

    @Override
    public SwimmingClub find(String id) throws Exception {
        SwimmingClub swimmingClub;
        Transaction transaction = null;


        if(id == null){
            logger.warn("Find data problem : id must not be 'null'");
            throw new Exception("Id must not be 'null'");
        }
        else {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                swimmingClub = session.find(SwimmingClub.class, id);
                transaction.commit();
            } catch (Exception e) {
                logger.warn("Exception raised in find : " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new Exception("Database connection error");
            }
        }
        if (swimmingClub == null){
            logger.warn("Swimming club (" + id + ") didn't find");
            throw new Exception("Swimming club didn't find");
        }
        return swimmingClub;
    }

    @Override
    public void create(SwimmingClub swimmingClub) throws Exception {
        Transaction transaction = null;
        checkDataConformity(swimmingClub, "Create");

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(swimmingClub);
            transaction.commit();
            logger.info("Swimming club (" + swimmingClub.getId() + ") added to data base");
        } catch (Exception e) {
            logger.warn("Exception raised in create : " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Database connection error");
        }
    }

    @Override
    public void delete(SwimmingClub swimmingClub) throws Exception {
        Transaction transaction = null;

        checkDataConformity(swimmingClub, "Delete");

        if(this.existById(swimmingClub.getId())) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(swimmingClub);
                transaction.commit();
                logger.info("Swimming club (" + swimmingClub.getId() + ") deleted from data base");
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
    public boolean existById(String id) throws Exception {
        SwimmingClub swimmingClub = this.find(id);
        return swimmingClub != null;
    }

    private void checkDataConformity(SwimmingClub swimmingClub, String methodName) throws Exception {
        if(swimmingClub == null){
            logger.warn(methodName + " data problem : swimming club must not be 'null'");
            throw new Exception("Swimming club must not be 'null'");
        }
        //TODO make a string to see all errors
        Set<ConstraintViolation<SwimmingClub>> constraintViolations = this.validator.validate(swimmingClub);
        for (ConstraintViolation<SwimmingClub> sc : constraintViolations) {
            logger.warn(methodName + " data problem : " + sc.getMessage());
            throw new Exception(sc.getMessage());
        }
    }
}
