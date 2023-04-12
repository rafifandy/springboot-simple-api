package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.Barang;
import com.enigma.livecodeecommerce.model.request.BarangRequest;
import com.enigma.livecodeecommerce.model.request.ValidList;
import com.enigma.livecodeecommerce.model.response.PagingResponse;
import com.enigma.livecodeecommerce.model.response.SuccessResponse;
import com.enigma.livecodeecommerce.service.BarangService;
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
@RequestMapping("/barang")
public class BarangController {
    @Autowired
    private BarangService barangService;
    @Autowired
    private KategoriService kategoriService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllBarang(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<Barang> barangs = barangService.findAll(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get barang list", barangs));
    }

    @PostMapping
    public ResponseEntity createBarang(@Valid @RequestBody BarangRequest barangRequest) {
        Barang newBarang = modelMapper.map(barangRequest, Barang.class);
        Barang result = barangService.save(newBarang);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Success create barang", result));
    }
    @PostMapping("/batch")
    public ResponseEntity createBarang(@RequestBody @Valid ValidList<BarangRequest> barangRequests) {
        List<Barang> barangs = new ArrayList<>();
        barangRequests.forEach(n-> barangs.add(modelMapper.map(n, Barang.class)));
        Iterable<Barang> results = barangService.saveAll(barangs);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("Success", results)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        Barang barang = barangService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success get barang with id " + id, barang));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Barang barang, @PathVariable Long id) throws Exception {
        Barang newBarang = barangService.update(barang, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<Barang>("Success update barang with id " + id, newBarang));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        Barang barang = barangService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success delete barang with id " + id, barang));
    }

    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value,
                                   @RequestParam("operator") String operator,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "DESC") String direction,
                                   @RequestParam(defaultValue = "id") String sortBy) throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(key, FindOperator.valueOf(operator), value);
        Page<Barang> barangs = barangService.findBy(searchCriteria,page, size, direction, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get all barang by", barangs));
    }

}
