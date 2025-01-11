package com.example.rentalfilm.Aktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aktor")
public class AktorController {

    @Autowired
    private AktorRepository aktorRepository;

    // Endpoint untuk mendapatkan semua aktor
    @GetMapping("/list")
    public List<Aktor> getAllAktor() {
        return aktorRepository.findAll();
    }

    // @GetMapping("/search-by-name")
    // @ResponseBody
    // public Aktor searchActorByName(@RequestParam("name") String name) {
    //     return aktorRepository.findByNama(name);
    // }

    @GetMapping("/search-by-name")
    @ResponseBody
    public List<Aktor> searchActorByName(@RequestParam("name") String name) {
        return aktorRepository.findByName(name); // Anda perlu menambahkan method findByName di AktorRepository
    }

    @GetMapping("/paginated-list")
    public List<Aktor> getAktorWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return aktorRepository.findAllWithPagination(page, size);
    }
}