package com.enigma.livecodeecommerce.controller;

import com.enigma.livecodeecommerce.model.entity.Harga;
import com.enigma.livecodeecommerce.model.request.HargaRequest;
import com.enigma.livecodeecommerce.model.request.ValidList;
import com.enigma.livecodeecommerce.model.response.PagingResponse;
import com.enigma.livecodeecommerce.model.response.SuccessResponse;
import com.enigma.livecodeecommerce.service.HargaService;
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
@RequestMapping("/harga")
public class HargaController {
    @Autowired
    private HargaService hargaService;
    @Autowired
    private KategoriService kategoriService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllHarga(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) throws Exception {
        Page<Harga> hargas = hargaService.findAll(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get harga list", hargas));
    }

    @PostMapping
    public ResponseEntity createHarga(@Valid @RequestBody HargaRequest hargaRequest) {
        Harga newHarga = modelMapper.map(hargaRequest, Harga.class);
        Harga result = hargaService.save(newHarga);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Success create harga", result));
    }
    @PostMapping("/batch")
    public ResponseEntity createHarga(@RequestBody @Valid ValidList<HargaRequest> hargaRequests) {
        List<Harga> hargas = new ArrayList<>();
        hargaRequests.forEach(n-> hargas.add(modelMapper.map(n, Harga.class)));
        Iterable<Harga> results = hargaService.saveAll(hargas);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("Success", results)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        Harga harga = hargaService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success get harga with id " + id, harga));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Harga harga, @PathVariable Long id) throws Exception {
        Harga newHarga = hargaService.update(harga, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<Harga>("Success update harga with id " + id, newHarga));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        Harga harga = hargaService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>("Success delete harga with id " + id, harga));
    }

    @GetMapping(params = {"key", "value", "operator"})
    public ResponseEntity getAllBy(@RequestParam("key") String key, @RequestParam("value") String value,
                                   @RequestParam("operator") String operator,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "DESC") String direction,
                                   @RequestParam(defaultValue = "id") String sortBy) throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(key, FindOperator.valueOf(operator), value);
        Page<Harga> hargas = hargaService.findBy(searchCriteria,page, size, direction, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new PagingResponse<>("Success get all harga by", hargas));
    }

}
