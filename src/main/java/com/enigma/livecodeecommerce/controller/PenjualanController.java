package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.Penjualan;
import com.enigma.livecodeecommerce.model.request.PenjualanRequest;
import com.enigma.livecodeecommerce.model.request.ValidList;
import com.enigma.livecodeecommerce.model.response.PagingResponse;
import com.enigma.livecodeecommerce.model.response.SuccessResponse;
import com.enigma.livecodeecommerce.service.PenjualanService;
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
@RequestMapping("/penjualan")
public class PenjualanController {
    @Autowired
    private PenjualanService penjualanService;
    @Autowired
    private KategoriService kategoriService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllPenjualan(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<Penjualan> penjualans = penjualanService.findAll(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get penjualan list", penjualans));
    }

    @PostMapping
    public ResponseEntity createPenjualan(@Valid @RequestBody PenjualanRequest penjualanRequest) {
        Penjualan newPenjualan = modelMapper.map(penjualanRequest, Penjualan.class);
        Penjualan result = penjualanService.save(newPenjualan);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Success create penjualan", result));
    }
    @PostMapping("/batch")
    public ResponseEntity createPenjualan(@RequestBody @Valid ValidList<PenjualanRequest> penjualanRequests) {
        List<Penjualan> penjualans = new ArrayList<>();
        penjualanRequests.forEach(n-> penjualans.add(modelMapper.map(n, Penjualan.class)));
        Iterable<Penjualan> results = penjualanService.saveAll(penjualans);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("Success", results)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        Penjualan penjualan = penjualanService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success get penjualan with id " + id, penjualan));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Penjualan penjualan, @PathVariable Long id) throws Exception {
        Penjualan newPenjualan = penjualanService.update(penjualan, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<Penjualan>("Success update penjualan with id " + id, newPenjualan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        Penjualan penjualan = penjualanService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success delete penjualan with id " + id, penjualan));
    }

    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value,
                                   @RequestParam("operator") String operator,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "DESC") String direction,
                                   @RequestParam(defaultValue = "id") String sortBy) throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(key, FindOperator.valueOf(operator), value);
        Page<Penjualan> penjualans = penjualanService.findBy(searchCriteria,page, size, direction, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get all penjualan by", penjualans));
    }

}
