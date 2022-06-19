package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = {SwimmingClubServiceImpl.class})
public class SwimmingClubServiceTest {

    @Autowired
    private SwimmingClubService swimmingClubService;

    @Test
    public void contextLoads(){
        assertNotNull(swimmingClubService);
    }

    @Test
    void findAll() {
        SwimmingClub swimmingClub1 = new SwimmingClub("TestSwimmingClub1");
        SwimmingClub swimmingClub2 = new SwimmingClub("TestSwimmingClub2");

        List<SwimmingClub> swimmingClubs = null;
        try {
            swimmingClubs = this.swimmingClubService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, swimmingClubs.size());

        try {
            this.swimmingClubService.create(swimmingClub1);
            swimmingClubs = this.swimmingClubService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, swimmingClubs.size());

        try {
            this.swimmingClubService.delete(swimmingClub1);
            swimmingClubs = this.swimmingClubService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, swimmingClubs.size());

        try {
            this.swimmingClubService.create(swimmingClub1);
            this.swimmingClubService.create(swimmingClub2);
            swimmingClubs = this.swimmingClubService.findAll();
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, swimmingClubs.size());

        try {
            this.swimmingClubService.delete(swimmingClub1);
            this.swimmingClubService.delete(swimmingClub2);
            this.swimmingClubService.findAll();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void find() {
        SwimmingClub swimmingClub1 = new SwimmingClub("TestSwimmingClub1");
        SwimmingClub swimmingClub2 = new SwimmingClub("TestSwimmingClub2");

        Exception exception = assertThrows(Exception.class, () -> this.swimmingClubService.find("Not in BDD"));
        assertEquals("Swimming club didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmingClubService.find(""));
        assertEquals("Swimming club didn't find", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmingClubService.find(null));
        assertEquals("Id must not be 'null'", exception.getMessage());


        SwimmingClub swimmingClub = null;
        SwimmingClub swimmingClubBis = null;
        try {
            this.swimmingClubService.create(swimmingClub1);
            swimmingClub = this.swimmingClubService.find(swimmingClub1.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(swimmingClub, swimmingClub1);
        assertNotEquals(swimmingClub, swimmingClub2);

        try {
            this.swimmingClubService.create(swimmingClub2);
            swimmingClub = this.swimmingClubService.find(swimmingClub1.getId());
            swimmingClubBis = this.swimmingClubService.find(swimmingClub2.getId());
        } catch (Exception e) {
            fail();
        }
        assertEquals(swimmingClub, swimmingClub1);
        assertEquals(swimmingClubBis, swimmingClub2);

        try {
            this.swimmingClubService.delete(swimmingClub1);
            this.swimmingClubService.delete(swimmingClub2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void create() {
        SwimmingClub swimmingClub1 = new SwimmingClub("TestSwimmingClub1");
        SwimmingClub swimmingClub2 = new SwimmingClub("");

        assertDoesNotThrow(() -> this.swimmingClubService.create(swimmingClub1));

        Exception exception = assertThrows(Exception.class, () -> this.swimmingClubService.create(swimmingClub2));
        assertTrue(exception.getMessage().contains("Name must be between"));

        SwimmingClub swimmingClub3 = new SwimmingClub(null);
        exception = assertThrows(Exception.class, () -> this.swimmingClubService.create(swimmingClub3));
        assertEquals("Swimming club name must not be 'null'", exception.getMessage());

        SwimmingClub swimmingClub4 = new SwimmingClub("A really long string more than 50 character for test");
        exception = assertThrows(Exception.class, () -> this.swimmingClubService.create(swimmingClub4));
        assertTrue(exception.getMessage().contains("Name must be between"));

        try {
            this.swimmingClubService.delete(swimmingClub1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void delete() {
        SwimmingClub swimmingClub1 = new SwimmingClub("TestSwimmingClub1");
        SwimmingClub swimmingClub2 = new SwimmingClub("TestSwimmingClub2");

        try {
            this.swimmingClubService.create(swimmingClub1);
        } catch (Exception e) {
            fail();
        }

        Exception exception = assertThrows(Exception.class, () -> this.swimmingClubService.delete(null));
        assertEquals("Swimming club must not be 'null'", exception.getMessage());

        exception = assertThrows(Exception.class, () -> this.swimmingClubService.delete(swimmingClub2));
        assertEquals("Id must not be 'null'", exception.getMessage());

        assertDoesNotThrow(() -> this.swimmingClubService.delete(swimmingClub1));

        exception = assertThrows(Exception.class, () -> this.swimmingClubService.delete(swimmingClub1));
        assertEquals("Swimming club didn't find", exception.getMessage());

    }
}
