package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;
import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Race;

import java.util.List;

public interface RaceService {
    List<Race> findAll() throws Exception;
    List<Race> findAllByCompetition(Competition competition) throws Exception;
    Race find(String id) throws Exception;
    void create(Race race) throws Exception;
    void delete(Race race) throws Exception;
    void update(Race race) throws Exception;
    boolean existById(String id) throws Exception;
}
