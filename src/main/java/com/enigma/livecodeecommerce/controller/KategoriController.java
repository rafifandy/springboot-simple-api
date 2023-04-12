package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.Kategori;
import com.enigma.livecodeecommerce.model.entity.Kategori;
import com.enigma.livecodeecommerce.model.request.KategoriRequest;
import com.enigma.livecodeecommerce.model.request.KategoriRequest;
import com.enigma.livecodeecommerce.model.request.ValidList;
import com.enigma.livecodeecommerce.model.response.PagingResponse;
import com.enigma.livecodeecommerce.model.response.SuccessResponse;
import com.enigma.livecodeecommerce.service.KategoriService;
import com.enigma.livecodeecommerce.util.constants.FindOperator;
import com.enigma.livecodeecommerce.util.specification.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kategori")
public class KategoriController {
    @Autowired
    private KategoriService kategoriService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllKategori(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<Kategori> kategoris = kategoriService.findAll(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get kategori list", kategoris));
    }

    @PostMapping
    public ResponseEntity createKategori(@Valid @RequestBody KategoriRequest kategoriRequest) {
        Kategori newKategori = modelMapper.map(kategoriRequest, Kategori.class);
        Kategori result = kategoriService.save(newKategori);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Success create kategori", result));
    }

    @PostMapping("/batch")
    public ResponseEntity createKategori(@RequestBody @Valid ValidList<KategoriRequest> kategoriRequests) {
        List<Kategori> kategoris = new ArrayList<>();
        kategoriRequests.forEach(n-> kategoris.add(modelMapper.map(n, Kategori.class)));
        Iterable<Kategori> results = kategoriService.saveAll(kategoris);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("Success", results)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        Kategori kategori = kategoriService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success get kategori with id " + id, kategori));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Kategori kategori, @PathVariable Long id) throws Exception {
        Kategori newKategori = kategoriService.update(kategori, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<Kategori>("Success update kategori with id " + id, newKategori));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        Kategori kategori = kategoriService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success delete kategori with id " + id, kategori));
    }

    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value,
                                   @RequestParam("operator") String operator,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "DESC") String direction,
                                   @RequestParam(defaultValue = "id") String sortBy) throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(key, FindOperator.valueOf(operator), value);
        Page<Kategori> kategoris = kategoriService.findBy(searchCriteria,page, size, direction, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get all kategori by", kategoris));
    }

}
