package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.DetailPenjualan;
import com.enigma.livecodeecommerce.model.request.DetailPenjualanRequest;
import com.enigma.livecodeecommerce.model.request.ValidList;
import com.enigma.livecodeecommerce.model.response.PagingResponse;
import com.enigma.livecodeecommerce.model.response.SuccessResponse;
import com.enigma.livecodeecommerce.service.DetailPenjualanService;
import com.enigma.livecodeecommerce.service.KategoriService;
import com.enigma.livecodeecommerce.util.constants.FindOperator;
import com.enigma.livecodeecommerce.util.specification.SearchCriteria;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/detailPenjualan")
public class DetailPenjualanController {
    @Autowired
    private DetailPenjualanService detailPenjualanService;
    @Autowired
    private KategoriService kategoriService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllDetailPenjualan(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<DetailPenjualan> detailPenjualans = detailPenjualanService.findAll(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get detailPenjualan list", detailPenjualans));
    }

    @PostMapping
    public ResponseEntity createDetailPenjualan(@Valid @RequestBody DetailPenjualanRequest detailPenjualanRequest) {
        DetailPenjualan newDetailPenjualan = modelMapper.map(detailPenjualanRequest, DetailPenjualan.class);
        DetailPenjualan result = detailPenjualanService.save(newDetailPenjualan);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Success create detailPenjualan", result));
    }
    @PostMapping("/batch")
    public ResponseEntity createDetailPenjualan(@RequestBody @Valid ValidList<DetailPenjualanRequest> detailPenjualanRequests) {
        List<DetailPenjualan> detailPenjualans = new ArrayList<>();
        detailPenjualanRequests.forEach(n-> detailPenjualans.add(modelMapper.map(n, DetailPenjualan.class)));
        Iterable<DetailPenjualan> results = detailPenjualanService.saveAll(detailPenjualans);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("Success", results)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        DetailPenjualan detailPenjualan = detailPenjualanService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success get detailPenjualan with id " + id, detailPenjualan));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody DetailPenjualan detailPenjualan, @PathVariable Long id) throws Exception {
        DetailPenjualan newDetailPenjualan = detailPenjualanService.update(detailPenjualan, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<DetailPenjualan>("Success update detailPenjualan with id " + id, newDetailPenjualan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        DetailPenjualan detailPenjualan = detailPenjualanService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success delete detailPenjualan with id " + id, detailPenjualan));
    }

    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value,
                                   @RequestParam("operator") String operator,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "DESC") String direction,
                                   @RequestParam(defaultValue = "id") String sortBy) throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(key, FindOperator.valueOf(operator), value);
        Page<DetailPenjualan> detailPenjualans = detailPenjualanService.findBy(searchCriteria,page, size, direction, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get all detailPenjualan by", detailPenjualans));
    }

}
