package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Competition;

import java.util.List;

public interface CompetitionService {
    public List<Competition> findAll() throws Exception;
    public Competition find(String id) throws Exception;
    public void create(Competition competition) throws Exception;
    public void delete(Competition competition) throws Exception;
    public void update(Competition competition) throws Exception;
    public boolean existById(String id) throws Exception;
}
