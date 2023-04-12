package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Harga;
import com.enigma.livecodeecommerce.repo.HargaRepo;
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
public class HargaService {
    @Autowired
    private HargaRepo hargaRepo;

    public Page<Harga> findAll(Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Harga> hargas = hargaRepo.findAll(pageable);
            if (hargas.isEmpty()) {
                throw new Exception("Harga is Empty");
            }
            return hargas;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public Harga findById(Long id) {

        try {
            Harga harga =  hargaRepo.findById(id).orElse(null);
            if (harga == null) {
                throw new Exception("Harga with id " + id + " not found");
            }
            return harga;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Harga save(Harga harga) {

        try {
            return hargaRepo.save(harga);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Iterable<Harga> saveAll(List<Harga> hargas) {

        try {
            return hargaRepo.saveAll(hargas);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Harga update (Harga harga, Long id) {

        try {
            Harga getHarga = hargaRepo.findById(id).orElse(null);
            if (getHarga == null) {
                throw new Exception("Harga with id " + id + " not found");
            }
            harga.setId(id);
            return hargaRepo.save(harga);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Harga delete(Long id) {

        try {
            Harga getHarga = hargaRepo.findById(id).orElse(null);
            if (getHarga == null) {
                throw new Exception("Harga with id " + id + " not found");
            }
            hargaRepo.delete(getHarga);
            return getHarga;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<Harga> findBy(SearchCriteria searchCriteria,
                                 Integer page, Integer size, String direction, String sortBy) {

        try {
            Specification specification = new Spec<Harga>().findBy(searchCriteria);
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Harga> hargas = hargaRepo.findAll(specification, pageable);
            if (hargas.isEmpty()) {
                throw new Exception("Harga not found");
            }
            return hargas;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

//    public List<Harga> findBy(SearchCriteria searchCriteria) {
//
//        try {
//            Specification specification = new Spec<Harga>().findBy(searchCriteria);
//            List<Harga> hargas = hargaRepo.findBy(specification);
//            if (hargas.isEmpty()) {
//                throw new Exception("Harga not found");
//            }
//            return hargas;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }





}
