package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.SwimmingClub;

import java.util.List;

public interface SwimmingClubService {
    public List<SwimmingClub> findAll() throws Exception;
    public SwimmingClub find(String id) throws Exception;
    public void create(SwimmingClub swimmingClub) throws Exception;
    public void delete(SwimmingClub swimmingClub) throws Exception;
    public boolean existById(String id) throws Exception;
}
