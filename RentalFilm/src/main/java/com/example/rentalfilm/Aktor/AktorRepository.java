package com.example.rentalfilm.Aktor;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AktorRepository {
    List<Aktor> findAll();

    void saveFilmActor(int filmId, int aktorId);

    Aktor findByNama(String nama);

    int findIdByNama(String nama);

    List<Aktor> findAllWithPagination(int page, int size);

    void saveAktor(String nama, byte[] foto);

    int findIdByName(String name);

    List<Aktor> findByName(String name);
}
