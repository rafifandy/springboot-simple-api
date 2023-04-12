package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.Penjualan;
import com.enigma.livecodeecommerce.model.response.LaporanResponse;
import com.enigma.livecodeecommerce.service.LaporanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/laporan")
public class LaporanController {
    @Autowired
    private LaporanService laporanService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping(params = {"start", "end"})
    public ResponseEntity laporan(@RequestParam("start") String start, @RequestParam("end") String end,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(defaultValue = "DESC") String direction,
                                  @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<Penjualan> penjualans = laporanService.laporan(
                java.sql.Date.valueOf(start), java.sql.Date.valueOf(end), page, size, direction, sortBy
        );
        Double total = laporanService.total(java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LaporanResponse<>("Success get laporan", total, penjualans));
    }
    @GetMapping("/today")
    public ResponseEntity laporanToday(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {

        java.util.Date today = new java.util.Date();

        Page<Penjualan> penjualans = laporanService.laporan(
                today, today ,page, size, direction, sortBy
        );

        Double total = laporanService.total(today, today);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LaporanResponse<>("Success get laporan", total, penjualans));
    }

    @GetMapping("/this-month")
    public ResponseEntity laporanThisMonth(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {

        LocalDate startLocal = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000) ).withDayOfMonth(1);
        LocalDate endLocal = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000) ).plusMonths(1).withDayOfMonth(1).minusDays(1);
        Date start = Date.from(startLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Page<Penjualan> penjualans = laporanService.laporan(
                start, end ,page, size, direction, sortBy
        );
        Double total = laporanService.total(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(new LaporanResponse<>("Success get laporan", total, penjualans));
    }

}
