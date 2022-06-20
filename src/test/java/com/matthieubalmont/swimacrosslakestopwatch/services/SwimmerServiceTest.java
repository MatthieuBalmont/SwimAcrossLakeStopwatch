package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Swimmer;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = {SwimmerServiceImpl.class, SwimmingClubServiceImpl.class})
public class SwimmerServiceTest {

    @Autowired
    private SwimmerService swimmerService;
    @Autowired
    private SwimmingClubService swimmingClubService;
    private SwimmingClub swimmingClub;

    @BeforeEach
    public void initObjects() {
        this.swimmingClub = new SwimmingClub("HCN Tullins");

        try {
            swimmingClubService.create(this.swimmingClub);
        } catch (Exception e) {
            fail();
        }
    }

    @AfterEach
    public void delObjects() {
        try {
            swimmingClubService.delete(this.swimmingClub);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void contextLoads(){
        assertNotNull(swimmerService);
    }

    @Test
    void findAll() {
        Swimmer swimmer1 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 1900, this.swimmingClub);
        Swimmer swimmer2 = new Swimmer("testFirstName2", "TestLastName1", "H", "FRA", 2020, this.swimmingClub);

        List<Swimmer> swimmers = null;
        try {
            swimmers = this.swimmerService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, swimmers.size());

        try {
            this.swimmerService.create(swimmer1);
            swimmers = this.swimmerService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, swimmers.size());

        try {
            this.swimmerService.delete(swimmer1);
            swimmers = this.swimmerService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, swimmers.size());

        try {
            this.swimmerService.create(swimmer1);
            this.swimmerService.create(swimmer2);
            swimmers = this.swimmerService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, swimmers.size());

        try {
            this.swimmerService.delete(swimmer1);
            this.swimmerService.delete(swimmer2);
            this.swimmerService.findAll();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void find() {
        Swimmer swimmer1 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 1900, this.swimmingClub);
        Swimmer swimmer2 = new Swimmer("testFirstName2", "TestLastName1", "H", "FRA", 2020, this.swimmingClub);

        Exception exception = assertThrows(Exception.class, () -> this.swimmerService.find("Not in BDD"));
        assertEquals("Swimmer didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmerService.find(""));
        assertEquals("Swimmer didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmerService.find(null));
        assertEquals("Id must not be 'null'", exception.getMessage());


        Swimmer swimmer = null;
        Swimmer swimmerBis = null;
        try {
            this.swimmerService.create(swimmer1);
            swimmer = this.swimmerService.find(swimmer1.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(swimmer, swimmer1);
        assertNotEquals(swimmer, swimmer2);

        try {
            this.swimmerService.create(swimmer2);
            swimmer = this.swimmerService.find(swimmer1.getId());
            swimmerBis = this.swimmerService.find(swimmer2.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(swimmer, swimmer1);
        assertEquals(swimmerBis, swimmer2);

        try {
            this.swimmerService.delete(swimmer1);
            this.swimmerService.delete(swimmer2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void create() {
        Swimmer swimmer1 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 1900, this.swimmingClub);
        Swimmer swimmer2 = new Swimmer("", "TestLastName1", "H", "FRA", 2020, this.swimmingClub);

        assertDoesNotThrow(() -> this.swimmerService.create(swimmer1));

        Exception exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer2));
        assertTrue(exception.getMessage().contains("First name must be between"));

        Swimmer swimmer3 = new Swimmer(null, "TestLastName1", "H", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer3));
        assertEquals("First name must not be 'null'", exception.getMessage());

        Swimmer swimmer4 = new Swimmer("A really long string more than 50 character for test", "TestLastName1", "H", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer4));
        assertTrue(exception.getMessage().contains("First name must be between"));

        Swimmer swimmer5 = new Swimmer("testFirstName1", "", "H", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer5));
        assertTrue(exception.getMessage().contains("Last name must be between"));

        Swimmer swimmer6 = new Swimmer("testFirstName1", null, "H", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer6));
        assertEquals("Last name must not be 'null'", exception.getMessage());

        Swimmer swimmer7 = new Swimmer("testFirstName1", "A really long string more than 50 character for test", "H", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer7));
        assertTrue(exception.getMessage().contains("Last name must be between"));

        Swimmer swimmer8 = new Swimmer("testFirstName1", "TestLastName1", "", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer8));
        assertTrue(exception.getMessage().contains("Genre must be between"));

        Swimmer swimmer9 = new Swimmer("testFirstName1", "TestLastName1", null, "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer9));
        assertEquals("Genre must not be 'null'", exception.getMessage());

        Swimmer swimmer10 = new Swimmer("testFirstName1", "TestLastName1", "HH", "FRA", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer10));
        assertTrue(exception.getMessage().contains("Genre must be between"));

        Swimmer swimmer11 = new Swimmer("testFirstName1", "TestLastName1", "H", "", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer11));
        assertTrue(exception.getMessage().contains("Nationality must be between"));

        Swimmer swimmer12 = new Swimmer("testFirstName1", "TestLastName1", "H", null, 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer12));
        assertEquals("Nationality must not be 'null'", exception.getMessage());

        Swimmer swimmer13 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA to long", 2020, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer13));
        assertTrue(exception.getMessage().contains("Nationality must be between"));

        Swimmer swimmer14 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", null, swimmingClub);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer14));
        assertEquals("Year of birth must not be 'null'", exception.getMessage());

        Swimmer swimmer15 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 2020, null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer15));
        assertEquals("Swimming club must not be 'null'", exception.getMessage());

        try {
            this.swimmerService.delete(swimmer1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void delete() {
        Swimmer swimmer1 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 1900, this.swimmingClub);
        Swimmer swimmer2 = new Swimmer("testFirstName2", "TestLastName1", "H", "FRA", 2020, this.swimmingClub);

        try {
            this.swimmerService.create(swimmer1);
        } catch (Exception e) {
            fail();
        }

        Exception exception = assertThrows(Exception.class, () -> this.swimmerService.delete(null));
        assertEquals("Swimmer must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmerService.delete(swimmer2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        assertDoesNotThrow(() -> this.swimmerService.delete(swimmer1));

        exception = assertThrows(Exception.class, () -> this.swimmerService.delete(swimmer1));
        assertEquals("Swimmer didn't find", exception.getMessage());

    }

    @Test
    void update() {
        Swimmer swimmer1 = new Swimmer("testFirstName1", "TestLastName1", "H", "FRA", 1900, this.swimmingClub);
        Swimmer swimmer2 = new Swimmer("testFirstName2", "TestLastName2", "H", "FRA", 2020, this.swimmingClub);

        assertDoesNotThrow(() -> this.swimmerService.create(swimmer1));

        swimmer1.setFirstName("");
        Exception exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("First name must be between"));

        exception = assertThrows(Exception.class, () -> this.swimmerService.update(swimmer2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        swimmer1.setFirstName(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("First name must not be 'null'", exception.getMessage());

        swimmer1.setFirstName("A really long string more than 50 character for test");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("First name must be between"));

        swimmer1.setFirstName("testFirstName1");
        swimmer1.setLastName("");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Last name must be between"));

        swimmer1.setLastName(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("Last name must not be 'null'", exception.getMessage());

        swimmer1.setLastName("A really long string more than 50 character for test");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Last name must be between"));

        swimmer1.setLastName("TestLastName1");
        swimmer1.setGenre("");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Genre must be between"));

        swimmer1.setGenre(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("Genre must not be 'null'", exception.getMessage());

        swimmer1.setGenre("HH");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Genre must be between"));

        swimmer1.setGenre("H");
        swimmer1.setNationality("");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Nationality must be between"));

        swimmer1.setNationality(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("Nationality must not be 'null'", exception.getMessage());

        swimmer1.setNationality("NAT to long");
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertTrue(exception.getMessage().contains("Nationality must be between"));

        swimmer1.setNationality("FRA");
        swimmer1.setBirthYear(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("Year of birth must not be 'null'", exception.getMessage());

        swimmer1.setBirthYear(2020);
        swimmer1.setSwimmingClub(null);
        exception = assertThrows(Exception.class, () -> this.swimmerService.create(swimmer1));
        assertEquals("Swimming club must not be 'null'", exception.getMessage());

        swimmer1.setSwimmingClub(this.swimmingClub);
        try {
            this.swimmerService.delete(swimmer1);
        } catch (Exception e) {
            fail();
        }
    }
}
