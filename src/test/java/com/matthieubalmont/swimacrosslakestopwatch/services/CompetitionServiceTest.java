package com.matthieubalmont.swimacrosslakestopwatch.services;


import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.services.CompetitionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CompetitionServiceImpl.class})
class CompetitionServiceTest {
    @Autowired
    private CompetitionService competitionModel;

    @Test
    public void contextLoads(){
        assertNotNull(competitionModel);
    }

    @Test
    void findAll() {
        Competition competition1 = new Competition("TestCompetition1", new GregorianCalendar(2022, Calendar.AUGUST, 16));
        Competition competition2 = new Competition("TestCompetition2", new GregorianCalendar(2021, Calendar.APRIL, 3));

        List<Competition> competitions = null;
        try {
            competitions = this.competitionModel.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, competitions.size());

        try {
            this.competitionModel.create(competition1);
            competitions = this.competitionModel.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, competitions.size());

        try {
            this.competitionModel.delete(competition1);
            competitions = this.competitionModel.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, competitions.size());

        try {
            this.competitionModel.create(competition1);
            this.competitionModel.create(competition2);
            competitions = this.competitionModel.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, competitions.size());

        try {
            this.competitionModel.delete(competition1);
            this.competitionModel.delete(competition2);
            this.competitionModel.findAll();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void find() {
        Competition competition1 = new Competition("TestCompetition1", new GregorianCalendar(2022, Calendar.AUGUST, 16));
        Competition competition2 = new Competition("TestCompetition2", new GregorianCalendar(2021, Calendar.APRIL, 3));

        Exception exception = assertThrows(Exception.class, () -> this.competitionModel.find("Not in BDD"));
        assertEquals("Competition didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.competitionModel.find(""));
        assertEquals("Competition didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.competitionModel.find("Not in BDD"));
        assertEquals("Competition didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.competitionModel.find(null));
        assertEquals("Id must not be 'null'", exception.getMessage());


        Competition competition = null;
        Competition competitionBis = null;
        try {
            this.competitionModel.create(competition1);
            competition = this.competitionModel.find(competition1.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(competition, competition1);
        assertNotEquals(competition, competition2);

        try {
            this.competitionModel.create(competition2);
            competition = this.competitionModel.find(competition1.getId());
            competitionBis = this.competitionModel.find(competition2.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(competition, competition1);
        assertEquals(competitionBis, competition2);

        try {
            this.competitionModel.delete(competition1);
            this.competitionModel.delete(competition2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void create() {
        Competition competition1 = new Competition("TestCompetition1", new GregorianCalendar(2022, Calendar.AUGUST, 16));
        Competition competition2 = new Competition("", new GregorianCalendar(2021, Calendar.APRIL, 3));

        assertDoesNotThrow(() -> this.competitionModel.create(competition1));

        Exception exception = assertThrows(Exception.class, () -> this.competitionModel.create(competition2));
        assertTrue(exception.getMessage().contains("Name must be between"));

        Competition competition3 = new Competition("az", new GregorianCalendar(2021, Calendar.APRIL, 3));
        exception = assertThrows(Exception.class, () -> this.competitionModel.create(competition3));
        assertTrue(exception.getMessage().contains("Name must be between"));

        Competition competition4 = new Competition("A really long string more than 50 character for test", new GregorianCalendar(2021, Calendar.APRIL, 3));
        exception = assertThrows(Exception.class, () -> this.competitionModel.create(competition4));
        assertTrue(exception.getMessage().contains("Name must be between"));

        exception = assertThrows(Exception.class, () -> this.competitionModel.create(null));
        assertEquals("Competition must not be 'null'", exception.getMessage());

        Competition competition5 = new Competition(null, new GregorianCalendar(2021, Calendar.APRIL, 3));
        exception = assertThrows(Exception.class, () -> this.competitionModel.create(competition5));
        assertEquals("Name must not be 'null'", exception.getMessage());

        Competition competition6 = new Competition("TestCompetition1", null);
        exception = assertThrows(Exception.class, () -> this.competitionModel.create(competition6));
        assertEquals("Date must not be 'null'", exception.getMessage());

        try {
            this.competitionModel.delete(competition1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void delete() {
        Competition competition1 = new Competition("TestCompetition1", new GregorianCalendar(2022, Calendar.AUGUST, 16));
        Competition competition2 = new Competition("TestCompetition2", new GregorianCalendar(2021, Calendar.APRIL, 3));

        try {
            this.competitionModel.create(competition1);
        } catch (Exception e) {
            fail();
        }

        Exception exception = assertThrows(Exception.class, () -> this.competitionModel.delete(null));
        assertEquals("Competition must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.competitionModel.delete(competition2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        assertDoesNotThrow(() -> this.competitionModel.delete(competition1));

        exception = assertThrows(Exception.class, () -> this.competitionModel.delete(competition1));
        assertEquals("Competition didn't find", exception.getMessage());

    }

    @Test
    void update() {
        Competition competition1 = new Competition("TestCompetition1", new GregorianCalendar(2022, Calendar.AUGUST, 16));
        Competition competition2 = new Competition("TestCompetition2", new GregorianCalendar(2021, Calendar.APRIL, 3));

        try {
            this.competitionModel.create(competition1);
        } catch (Exception e) {
            fail();
        }

        competition1.setName("UpdateTestCompetition1");
        assertDoesNotThrow(() -> this.competitionModel.update(competition1));

        Exception exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.competitionModel.update(null));
        assertEquals("Competition must not be 'null'", exception.getMessage());

        competition1.setDate(null);
        exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition1));
        assertEquals("Date must not be 'null'", exception.getMessage());

        competition1.setDate(new GregorianCalendar(2022, Calendar.AUGUST, 16));
        competition1.setName(null);
        exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition1));
        assertEquals("Name must not be 'null'", exception.getMessage());

        competition1.setName("");
        exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition1));
        assertTrue(exception.getMessage().contains("Name must be between"));

        competition1.setName("aa");
        exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition1));
        assertTrue(exception.getMessage().contains("Name must be between"));

        competition1.setName("A really long string more than 50 character for test");
        exception = assertThrows(Exception.class, () -> this.competitionModel.update(competition1));
        assertTrue(exception.getMessage().contains("Name must be between"));

        competition1.setName("UpdateTestCompetition1");
        try {
            this.competitionModel.delete(competition1);
        } catch (Exception e) {
            fail();
        }
    }
}