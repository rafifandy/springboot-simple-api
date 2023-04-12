package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Kategori;
import com.enigma.livecodeecommerce.repo.KategoriRepo;
import com.enigma.livecodeecommerce.util.specification.SearchCriteria;
import com.enigma.livecodeecommerce.util.specification.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KategoriService {
    @Autowired
    private KategoriRepo kategoriRepo;

    public Page<Kategori> findAll(Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Kategori> kategoris = kategoriRepo.findAll(pageable);
            if (kategoris.isEmpty()) {
                throw new Exception("Kategori is Empty");
            }
            return kategoris;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public Kategori findById(Long id) {

        try {
            Kategori kategori =  kategoriRepo.findById(id).orElse(null);
            if (kategori == null) {
                throw new Exception("Kategori with id " + id + " not found");
            }
            return kategori;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Kategori save(Kategori kategori) {

        try {
            return kategoriRepo.save(kategori);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Iterable<Kategori> saveAll(List<Kategori> kategoris) {

        try {
            return kategoriRepo.saveAll(kategoris);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Kategori update (Kategori kategori, Long id) {

        try {
            Kategori getKategori = kategoriRepo.findById(id).orElse(null);
            if (getKategori == null) {
                throw new Exception("Kategori with id " + id + " not found");
            }
            kategori.setId(id);
            return kategoriRepo.save(kategori);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Kategori delete(Long id) {

        try {
            Kategori getKategori = kategoriRepo.findById(id).orElse(null);
            if (getKategori == null) {
                throw new Exception("Kategori with id " + id + " not found");
            }
            kategoriRepo.delete(getKategori);
            return getKategori;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<Kategori> findBy(SearchCriteria searchCriteria,
                                 Integer page, Integer size, String direction, String sortBy) {

        try {
            Specification specification = new Spec<Kategori>().findBy(searchCriteria);
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Kategori> kategoris = kategoriRepo.findAll(specification, pageable);
            if (kategoris.isEmpty()) {
                throw new Exception("Kategori not found");
            }
            return kategoris;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

//    public List<Kategori> findBy(SearchCriteria searchCriteria) {
//
//        try {
//            Specification specification = new Spec<Kategori>().findBy(searchCriteria);
//            List<Kategori> kategoris = kategoriRepo.findBy(specification);
//            if (kategoris.isEmpty()) {
//                throw new Exception("Kategori not found");
//            }
//            return kategoris;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }





}
