package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Barang;
import com.enigma.livecodeecommerce.repo.BarangRepo;
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
public class BarangService {
    @Autowired
    private BarangRepo barangRepo;

    public Page<Barang> findAll(Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Barang> barangs = barangRepo.findAll(pageable);
            if (barangs.isEmpty()) {
                throw new Exception("Barang is Empty");
            }
            return barangs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public Barang findById(Long id) {

        try {
            Barang barang =  barangRepo.findById(id).orElse(null);
            if (barang == null) {
                throw new Exception("Barang with id " + id + " not found");
            }
            return barang;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Barang save(Barang barang) {

        try {
            return barangRepo.save(barang);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Iterable<Barang> saveAll(List<Barang> barangs) {

        try {
            return barangRepo.saveAll(barangs);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Barang update (Barang barang, Long id) {

        try {
            Barang getBarang = barangRepo.findById(id).orElse(null);
            if (getBarang == null) {
                throw new Exception("Barang with id " + id + " not found");
            }
            barang.setId(id);
            return barangRepo.save(barang);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Barang delete(Long id) {

        try {
            Barang getBarang = barangRepo.findById(id).orElse(null);
            if (getBarang == null) {
                throw new Exception("Barang with id " + id + " not found");
            }
            barangRepo.delete(getBarang);
            return getBarang;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<Barang> findBy(SearchCriteria searchCriteria,
                                 Integer page, Integer size, String direction, String sortBy) {

        try {
            Specification specification = new Spec<Barang>().findBy(searchCriteria);
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Barang> barangs = barangRepo.findAll(specification, pageable);
            if (barangs.isEmpty()) {
                throw new Exception("Barang not found");
            }
            return barangs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

//    public List<Barang> findBy(SearchCriteria searchCriteria) {
//
//        try {
//            Specification specification = new Spec<Barang>().findBy(searchCriteria);
//            List<Barang> barangs = barangRepo.findBy(specification);
//            if (barangs.isEmpty()) {
//                throw new Exception("Barang not found");
//            }
//            return barangs;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }





}
