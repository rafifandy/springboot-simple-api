package com.enigma.livecodeecommerce.repo;

import com.enigma.livecodeecommerce.model.entity.Penjualan;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PenjualanRepo extends JpaRepository<Penjualan, Long>, JpaSpecificationExecutor<Penjualan> {
    @Query("select p from Penjualan p where p.date between :start and :end")
    public Page<Penjualan> dateFilter(@PathParam("start") Date start, @PathParam("end") Date end, Pageable pageable);

}

