package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;

import java.util.List;

public interface CompetitionService {
    List<Competition> findAll() throws Exception;
    Competition find(String id) throws Exception;
    void create(Competition competition) throws Exception;
    void delete(Competition competition) throws Exception;
    void update(Competition competition) throws Exception;
    boolean existById(String id) throws Exception;
    void setCurrentCompetition(Competition competition);
    Competition getCurrentCompetition();
}
