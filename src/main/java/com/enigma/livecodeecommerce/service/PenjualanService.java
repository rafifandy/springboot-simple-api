package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Penjualan;
import com.enigma.livecodeecommerce.repo.DetailPenjualanRepo;
import com.enigma.livecodeecommerce.repo.PenjualanRepo;
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
public class PenjualanService {
    @Autowired
    private PenjualanRepo penjualanRepo;
    @Autowired
    private DetailPenjualanRepo detailPenjualanRepo;

    public Page<Penjualan> findAll(Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);

            Page<Penjualan> penjualans = penjualanRepo.findAll(pageable);

            if (penjualans.isEmpty()) {
                throw new Exception("Penjualan is Empty");
            }

            penjualans.forEach(n->{
                n.setTotalQty(detailPenjualanRepo.totalQty(n.getId()));
                n.setTotalPrice(detailPenjualanRepo.totalPrice(n.getId()));
                n.getDetailPenjualan().forEach(d->d.setSubTotal(detailPenjualanRepo.subTotal(d.getId())));
            });

            return penjualans;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Penjualan findById(Long id) {

        try {
            Penjualan penjualan =  penjualanRepo.findById(id).orElse(null);
            if (penjualan == null) {
                throw new Exception("Penjualan with id " + id + " not found");
            }
            penjualan.setTotalQty(detailPenjualanRepo.totalQty(id));
            penjualan.setTotalPrice(detailPenjualanRepo.totalPrice(id));
            penjualan.getDetailPenjualan().forEach(
                    d-> d.setSubTotal(detailPenjualanRepo.subTotal(d.getId()))
            );

            return penjualan;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Penjualan save(Penjualan penjualan) {

        try {
            return penjualanRepo.save(penjualan);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Iterable<Penjualan> saveAll(List<Penjualan> penjualans) {

        try {
            return penjualanRepo.saveAll(penjualans);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Penjualan update (Penjualan penjualan, Long id) {

        try {
            Penjualan getPenjualan = penjualanRepo.findById(id).orElse(null);
            if (getPenjualan == null) {
                throw new Exception("Penjualan with id " + id + " not found");
            }
            penjualan.setId(id);
            return penjualanRepo.save(penjualan);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Penjualan delete(Long id) {

        try {
            Penjualan getPenjualan = penjualanRepo.findById(id).orElse(null);
            if (getPenjualan == null) {
                throw new Exception("Penjualan with id " + id + " not found");
            }
            penjualanRepo.delete(getPenjualan);
            return getPenjualan;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<Penjualan> findBy(SearchCriteria searchCriteria,
                                 Integer page, Integer size, String direction, String sortBy) {

        try {
            Specification specification = new Spec<Penjualan>().findBy(searchCriteria);
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<Penjualan> penjualans = penjualanRepo.findAll(specification, pageable);
            if (penjualans.isEmpty()) {
                throw new Exception("Penjualan not found");
            }
            return penjualans;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

//    public List<Penjualan> findBy(SearchCriteria searchCriteria) {
//
//        try {
//            Specification specification = new Spec<Penjualan>().findBy(searchCriteria);
//            List<Penjualan> penjualans = penjualanRepo.findBy(specification);
//            if (penjualans.isEmpty()) {
//                throw new Exception("Penjualan not found");
//            }
//            return penjualans;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }





}
