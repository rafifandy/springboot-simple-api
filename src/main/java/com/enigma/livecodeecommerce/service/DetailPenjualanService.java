package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Barang;
import com.enigma.livecodeecommerce.model.entity.DetailPenjualan;
import com.enigma.livecodeecommerce.model.entity.Harga;
import com.enigma.livecodeecommerce.repo.BarangRepo;
import com.enigma.livecodeecommerce.repo.DetailPenjualanRepo;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DetailPenjualanService {
    @Autowired
    private DetailPenjualanRepo detailPenjualanRepo;
    @Autowired
    private BarangService barangService;
    @Autowired
    private HargaService hargaService;

    public Page<DetailPenjualan> findAll(Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<DetailPenjualan> detailPenjualans = detailPenjualanRepo.findAll(pageable);

            if (detailPenjualans.isEmpty()) {
                throw new Exception("DetailPenjualan is Empty");
            }

            detailPenjualans.forEach(n->{
                n.setSubTotal(detailPenjualanRepo.subTotal(n.getId()));
            });

            return detailPenjualans;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public DetailPenjualan findById(Long id) {

        try {
            DetailPenjualan detailPenjualan =  detailPenjualanRepo.findById(id).orElse(null);
            if (detailPenjualan == null) {
                throw new Exception("DetailPenjualan with id " + id + " not found");
            }

            detailPenjualan.setSubTotal(detailPenjualanRepo.subTotal(id));

            return detailPenjualan;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public DetailPenjualan save(DetailPenjualan detailPenjualan) {

        try {
            Harga harga = hargaService.findById(detailPenjualan.getHarga().getId());
            Barang barang = barangService.findById(harga.getBarang().getId());

            if (barang.getStock() < detailPenjualan.getQty()) {
                throw new Exception("Stok tidak mencukupi");
            }

            barang.setStock( barang.getStock() - detailPenjualan.getQty() );
            barangService.save(barang);
            return detailPenjualanRepo.save(detailPenjualan);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Iterable<DetailPenjualan> saveAll(List<DetailPenjualan> detailPenjualans) {

        try {
            List<Barang> barangs = new ArrayList<>();
            for (int i = 0; i<detailPenjualans.size(); i++) {
                Harga harga = hargaService.findById(detailPenjualans.get(i).getHarga().getId());
                barangs.add(barangService.findById(harga.getBarang().getId()));
                if (barangs.get(i).getStock() < detailPenjualans.get(i).getQty()) {
                    throw new Exception("Stok tidak mencukupi");
                }
                barangs.get(i).setStock(barangs.get(i).getStock() - detailPenjualans.get(i).getQty());
            }
            barangService.saveAll(barangs);
            return detailPenjualanRepo.saveAll(detailPenjualans);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public DetailPenjualan update (DetailPenjualan detailPenjualan, Long id) {

        try {
            DetailPenjualan getDetailPenjualan = detailPenjualanRepo.findById(id).orElse(null);
            if (getDetailPenjualan == null) {
                throw new Exception("DetailPenjualan with id " + id + " not found");
            }
            detailPenjualan.setId(id);
            return detailPenjualanRepo.save(detailPenjualan);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public DetailPenjualan delete(Long id) {

        try {
            DetailPenjualan getDetailPenjualan = detailPenjualanRepo.findById(id).orElse(null);
            if (getDetailPenjualan == null) {
                throw new Exception("DetailPenjualan with id " + id + " not found");
            }
            Harga harga = hargaService.findById(getDetailPenjualan.getHarga().getId());
            Barang barang = barangService.findById(harga.getBarang().getId());

            barang.setStock(barang.getStock() + getDetailPenjualan.getQty());
            barangService.save(barang);

            detailPenjualanRepo.delete(getDetailPenjualan);
            return getDetailPenjualan;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<DetailPenjualan> findBy(SearchCriteria searchCriteria,
                                 Integer page, Integer size, String direction, String sortBy) {

        try {
            Specification specification = new Spec<DetailPenjualan>().findBy(searchCriteria);
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);
            Page<DetailPenjualan> detailPenjualans = detailPenjualanRepo.findAll(specification, pageable);
            if (detailPenjualans.isEmpty()) {
                throw new Exception("DetailPenjualan not found");
            }
            return detailPenjualans;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

//    public List<DetailPenjualan> findBy(SearchCriteria searchCriteria) {
//
//        try {
//            Specification specification = new Spec<DetailPenjualan>().findBy(searchCriteria);
//            List<DetailPenjualan> detailPenjualans = detailPenjualanRepo.findBy(specification);
//            if (detailPenjualans.isEmpty()) {
//                throw new Exception("DetailPenjualan not found");
//            }
//            return detailPenjualans;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }





}
