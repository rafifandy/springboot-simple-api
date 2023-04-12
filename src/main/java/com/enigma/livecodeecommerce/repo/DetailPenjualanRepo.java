package com.enigma.livecodeecommerce.repo;

import com.enigma.livecodeecommerce.model.entity.DetailPenjualan;
import com.enigma.livecodeecommerce.model.entity.Penjualan;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DetailPenjualanRepo extends JpaRepository<DetailPenjualan, Long>, JpaSpecificationExecutor {

    @Query("select sum(d.qty * d.harga.price) from DetailPenjualan d where d.penjualan.date between :start and :end")
    public Double total(@PathParam("start") Date start, @PathParam("end") Date end);

    @Query("select sum(d.qty) from DetailPenjualan d where d.penjualan.id = :penjualanId")
    public Double totalQty(@PathParam("penjualanId") Long penjualanId);

    @Query("select sum(d.qty * d.harga.price) from DetailPenjualan d where d.penjualan.id = :penjualanId")
    public Double totalPrice(@PathParam("penjualanId") Long penjualanId);

    @Query("select sum(d.qty * d.harga.price) from DetailPenjualan d where d.id = :id")
    public Double subTotal(@PathParam("penjualanId") Long id);
}

