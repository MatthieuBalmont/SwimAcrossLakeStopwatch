package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = {RaceServiceImpl.class, CompetitionServiceImpl.class})
public class RaceServiceTest {
    @Autowired
    private RaceService raceService;
    @Autowired
    private CompetitionService competitionService;
    private Competition competition;

    @Test
    public void contextLoads(){
        assertNotNull(raceService);
    }

    @BeforeEach
    public void initObjects() {
        this.competition = new Competition("TestCompetition", new GregorianCalendar(2022, Calendar.AUGUST, 16));

        assertDoesNotThrow(() -> this.competitionService.create(this.competition));
    }

    @AfterEach
    public void delObjects() {
        assertDoesNotThrow(() -> this.competitionService.delete(this.competition));
    }

    @Test
    void findAll() {
        Race race1 = new Race(1600, 1, this.competition);
        Race race2 = new Race(3200, 2, this.competition);

        List<Race> races = null;
        try {
            races = this.raceService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, races.size());

        try {
            this.raceService.create(race1);
            races = this.raceService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, races.size());

        try {
            this.raceService.delete(race1);
            races = this.raceService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, races.size());

        try {
            this.raceService.create(race1);
            this.raceService.create(race2);
            races = this.raceService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, races.size());

        try {
            this.raceService.delete(race1);
            this.raceService.delete(race2);
            this.raceService.findAll();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllByCompetition() {
        Race race1 = new Race(1600, 1, this.competition);
        Competition competition2 = new Competition("TestCompetition2", new GregorianCalendar(2020, Calendar.AUGUST, 16));
        assertDoesNotThrow(() -> this.competitionService.create(competition2));
        Race race2 = new Race(3200, 2, competition2);

        List<Race> races = null;
        try {
            races = this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, races.size());

        try {
            this.raceService.create(race1);
            races = this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, races.size());

        try {
            this.raceService.delete(race1);
            races = this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, races.size());

        try {
            this.raceService.create(race1);
            this.raceService.create(race2);
            races = this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, races.size());

        try {
            this.raceService.delete(race1);
            races = this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }

        assertEquals(0, races.size());

        try {
            this.raceService.delete(race2);
            this.raceService.findAllByCompetition(this.competition);
        } catch (Exception e) {
            fail();
        }

        Competition competition3 = new Competition("TestCompetition3", new GregorianCalendar(2020, Calendar.AUGUST, 16));
        Exception exception = assertThrows(Exception.class, () -> this.raceService.findAllByCompetition(competition3));
        assertEquals("Competition id must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.raceService.findAllByCompetition(null));
        assertEquals("Competition must not be 'null'", exception.getMessage());
    }

    @Test
    void find() {
        Race race1 = new Race(1600, 1, this.competition);
        Race race2 = new Race(3200, 2, this.competition);

        Exception exception = assertThrows(Exception.class, () -> this.raceService.find("Not in BDD"));
        assertEquals("Race didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.raceService.find(""));
        assertEquals("Race didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.raceService.find(null));
        assertEquals("Id must not be 'null'", exception.getMessage());


        Race race = null;
        Race raceBis = null;
        try {
            this.raceService.create(race1);
            race = this.raceService.find(race1.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(race, race1);
        assertNotEquals(race, race2);

        try {
            this.raceService.create(race2);
            race = this.raceService.find(race1.getId());
            raceBis = this.raceService.find(race2.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(race, race1);
        assertEquals(raceBis, race2);

        try {
            this.raceService.delete(race1);
            this.raceService.delete(race2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void create() {
        Race race1 = new Race(1600, 1, this.competition);
        Race race2 = new Race(-1, 2, this.competition);

        assertDoesNotThrow(() -> this.raceService.create(race1));

        Exception exception = assertThrows(Exception.class, () -> this.raceService.create(race2));
        assertTrue(exception.getMessage().contains("Distance must be higher that"));

        Race race3 = new Race(3200, -1, this.competition);
        exception = assertThrows(Exception.class, () -> this.raceService.create(race3));
        assertTrue(exception.getMessage().contains("Start order must be higher that"));

        Race race4 = new Race(3200, 1, null);
        exception = assertThrows(Exception.class, () -> this.raceService.create(race4));
        assertEquals("Competition must not be 'null'", exception.getMessage());

        try {
            this.raceService.delete(race1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void delete() {
        Race race1 = new Race(1600, 1, this.competition);
        Race race2 = new Race(3200, 2, this.competition);

        try {
            this.raceService.create(race1);
        } catch (Exception e) {
            fail();
        }

        Exception exception = assertThrows(Exception.class, () -> this.raceService.delete(null));
        assertEquals("Race must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.raceService.delete(race2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        assertDoesNotThrow(() -> this.raceService.delete(race1));

        exception = assertThrows(Exception.class, () -> this.raceService.delete(race1));
        assertEquals("Race didn't find", exception.getMessage());

    }

    @Test
    void update() {
        Race race1 = new Race(1600, 1, this.competition);
        Race race2 = new Race(3200, 2, this.competition);

        assertDoesNotThrow(() -> this.raceService.create(race1));

        Exception exception = assertThrows(Exception.class, () -> this.raceService.update(race2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        race1.setDistance(-1);
        exception = assertThrows(Exception.class, () -> this.raceService.create(race1));
        assertTrue(exception.getMessage().contains("Distance must be higher that"));

        race1.setDistance(3200);
        race1.setStartOrder(-1);
        exception = assertThrows(Exception.class, () -> this.raceService.create(race1));
        assertTrue(exception.getMessage().contains("Start order must be higher that"));

        race1.setStartOrder(1);
        race1.setCompetition(null);
        exception = assertThrows(Exception.class, () -> this.raceService.create(race1));
        assertEquals("Competition must not be 'null'", exception.getMessage());

        race1.setCompetition(this.competition);
        try {
            this.raceService.delete(race1);
        } catch (Exception e) {
            fail();
        }
    }
}
