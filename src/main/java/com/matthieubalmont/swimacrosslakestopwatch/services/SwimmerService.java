package com.matthieubalmont.swimacrosslakestopwatch.services;

import com.matthieubalmont.swimacrosslakestopwatch.hibernate.entities.Swimmer;

import java.util.List;

public interface SwimmerService {
    public List<Swimmer> findAll() throws Exception;
    public Swimmer find(String id) throws Exception;
    public void create(Swimmer swimmer) throws Exception;
    public void delete(Swimmer swimmer) throws Exception;
    public void update(Swimmer swimmer) throws Exception;
    public boolean existById(String id) throws Exception;
}
