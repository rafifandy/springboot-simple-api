package com.enigma.livecodeecommerce.service;

import com.enigma.livecodeecommerce.model.entity.Penjualan;
import com.enigma.livecodeecommerce.repo.DetailPenjualanRepo;
import com.enigma.livecodeecommerce.repo.PenjualanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LaporanService {
    @Autowired
    private PenjualanRepo penjualanRepo;
    @Autowired
    private DetailPenjualanRepo detailPenjualanRepo;


    public Double total(Date start, Date end) {

        try {
            return detailPenjualanRepo.total(start,end);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public Page<Penjualan> laporan(Date start, Date end,Integer page, Integer size, String direction, String sortBy) {

        try {
            Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
            Pageable pageable = PageRequest.of((page - 1), size, sort);

            Page<Penjualan> penjualans = penjualanRepo.dateFilter(start,end,pageable);

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







}
